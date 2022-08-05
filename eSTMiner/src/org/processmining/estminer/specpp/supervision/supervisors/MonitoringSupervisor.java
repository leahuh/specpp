package org.processmining.estminer.specpp.supervision.supervisors;

import org.processmining.estminer.specpp.supervision.monitoring.Monitor;
import org.processmining.estminer.specpp.supervision.observations.Observation;
import org.processmining.estminer.specpp.supervision.traits.Monitoring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class MonitoringSupervisor extends SubSupervisor implements Monitoring {

    protected final Map<String, Monitor<?, ?>> monitorMap;

    public MonitoringSupervisor() {
        monitorMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    protected <O extends Observation, R> Monitor<O, R> getMonitor(String label) {
        return (Monitor<O, R>) monitorMap.get(label);
    }

    protected <O extends Observation, R> void createMonitor(String label, Monitor<O, R> monitor) {
        monitorMap.put(label, monitor);
    }

    @Override
    public Collection<Monitor<?, ?>> getMonitors() {
        return monitorMap.values();
    }

}
