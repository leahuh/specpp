package org.processmining.estminer.specpp.representations.log.impls;

import org.processmining.estminer.specpp.representations.BitMask;
import org.processmining.estminer.specpp.representations.encoding.IndexSubset;
import org.processmining.estminer.specpp.representations.encoding.IntEncoding;
import org.processmining.estminer.specpp.representations.encoding.IntEncodings;
import org.processmining.estminer.specpp.representations.log.*;
import org.processmining.estminer.specpp.representations.petri.Transition;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class LogEncoder {

    public static class LogEncodingParameters {

        private final boolean discardEmptyVariants, filterByEncodedActivities;

        public LogEncodingParameters(boolean discardEmptyVariants, boolean filterByEncodedActivities) {
            this.discardEmptyVariants = discardEmptyVariants;
            this.filterByEncodedActivities = filterByEncodedActivities;
        }

        public boolean discardEmptyVariants() {
            return discardEmptyVariants;
        }

        public boolean filterByEncodedActivities() {
            return filterByEncodedActivities;
        }

    }


    public static class LogEncodingInfo {

        private final Set<Activity> encodedActivities;

        public LogEncodingInfo(Set<Activity> encodedActivities) {
            this.encodedActivities = encodedActivities;
        }

        public Set<Activity> getEncodedActivities() {
            return encodedActivities;
        }
    }


    public static EncodedLog encodeLog(Log log, IntEncoding<Activity> encoding, LogEncodingParameters lep, LogEncodingInfo lei) {
        BitMask mask = log.variantIndices().copy();
        Set<Activity> activitySet = lei.getEncodedActivities();
        ArrayList<Integer> cumLengthsList = new ArrayList<>();
        ArrayList<Integer> dataList = new ArrayList<>();
        boolean discardedVariant = false;
        int acc = 0;
        cumLengthsList.add(acc);
        for (IndexedVariant indexedVariant : log) {
            Variant variant = indexedVariant.getItem();
            int length = 0;
            for (Activity activity : variant) {
                if (!lep.filterByEncodedActivities() || activitySet.contains(activity)) {
                    if (encoding.isInDomain(activity)) dataList.add(encoding.encode(activity));
                    else dataList.add(IntEncoding.OUTSIDE_RANGE);
                    ++length;
                }
            }
            if (length == 0 && lep.discardEmptyVariants()) {
                mask.clear(indexedVariant.getIndex());
                discardedVariant = true;
            } else {
                acc += length;
                cumLengthsList.add(acc);
            }
        }
        int[] data = dataList.stream().mapToInt(i -> i).toArray();
        int[] startIndices = cumLengthsList.stream().mapToInt(i -> i).toArray();
        return (log instanceof OnlyCoversIndexSubset || discardedVariant) ? new EncodedSubLog(IndexSubset.of(mask), data, startIndices, encoding) : new EncodedLog(data, startIndices, encoding);
    }

    public static MultiEncodedLog multiEncodeLog(Log log, IntEncodings<Transition> transitionEncodings, Map<Activity, Transition> mapping, LogEncodingParameters lep) {
        IntEncodings<Activity> activityEncodings = IntEncodings.mapEncodings(transitionEncodings, mapping);
        return multiEncodeLog(log, activityEncodings, lep);
    }

    public static MultiEncodedLog multiEncodeLog(Log log, IntEncodings<Activity> activityEncodings, LogEncodingParameters lep) {
        Set<Activity> activitySet = activityEncodings.combinedDomain();
        LogEncodingInfo lei = new LogEncodingInfo(activitySet);
        EncodedLog presetEncodedLog = encodeLog(log, activityEncodings.pre(), lep, lei);
        EncodedLog postsetEncodedLog = encodeLog(log, activityEncodings.post(), lep, lei);
        if (presetEncodedLog instanceof EncodedSubLog && postsetEncodedLog instanceof EncodedSubLog) {
            IndexSubset indexSubset = ((OnlyCoversIndexSubset) presetEncodedLog).getIndexSubset();
            return new MultiEncodedSubLog(indexSubset, ((EncodedSubLog) presetEncodedLog), ((EncodedSubLog) postsetEncodedLog), activityEncodings);
        } else return new MultiEncodedLog(presetEncodedLog, postsetEncodedLog, activityEncodings);
    }

}
