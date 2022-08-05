package org.processmining.estminer.specpp.componenting.system;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.processmining.estminer.specpp.componenting.delegators.Container;
import org.processmining.estminer.specpp.componenting.traits.ProvisionsComponents;
import org.processmining.estminer.specpp.componenting.traits.RequiresComponents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawuse", "unchecked"})
public class ComponentSystemAdapter implements RequiresComponents, ProvisionsComponents {

    private final Map<ComponentType, FulfilledRequirementsCollection<?>> componentProvisions;
    private final Table<ComponentType, Requirement<?, ?>, Container<?>> componentRequirements;


    public ComponentSystemAdapter() {
        componentRequirements = HashBasedTable.create();
        componentProvisions = new HashMap<>();
    }

    public boolean areAllRequirementsMet() {
        return componentRequirements.values().stream().allMatch(Container::isNonEmpty);
    }

    public void consumeEntirely(ComponentSystemAdapter other) {
        componentRequirements.putAll(other.componentRequirements);
        componentProvisions.putAll(other.componentProvisions);
    }

    public void addComponent(ComponentType componentType) {
        componentProvisions.put(componentType, componentType.createCollection());
    }

    public <R extends Requirement<?, R>> ComponentSystemAdapter provide(FulfilledRequirement<?, R> req) {
        ComponentType componentType = req.componentType();
        if (!componentProvisions.containsKey(componentType)) addComponent(componentType);

        FulfilledRequirementsCollection<R> collection = (FulfilledRequirementsCollection<R>) componentProvisions.get(componentType);
        collection.add(req);
        return this;
    }

    public <R extends Requirement<?, R>> ComponentSystemAdapter require(Requirement<?, R> r, Container<?> delegator) {
        componentRequirements.put(r.componentType(), r, delegator);
        return this;
    }

    public <R extends Requirement<?, R>> FulfilledRequirementsCollection<R> getProvisions(ComponentType componentType) {
        return (FulfilledRequirementsCollection<R>) componentProvisions.get(componentType);
    }

    public void fulfil(RequiresComponents other) {
        for (FulfilledRequirementsCollection<?> frp : componentProvisions().values()) {
            other.instantiateFrom(frp);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <C, R extends Requirement<? extends C, R>> void fulfil(R requirement, Container<C> container) {
        FulfilledRequirementsCollection frp = componentProvisions().get(requirement.componentType());
        if (frp != null) {
            List<FulfilledRequirement<?, R>> list = frp.multiSatisfyRequirement(requirement);
            for (FulfilledRequirement<?, R> f : list) {
                if (container.isFull()) return;
                instantiateWith(requirement, container, f);
            }
        }
    }

    public void fulfilFrom(ProvisionsComponents other) {
        for (FulfilledRequirementsCollection<?> frp : other.componentProvisions().values()) {
            fulfilFrom(frp);
        }
    }

    public <R extends Requirement<?, R>> void fulfilFrom(FulfilledRequirementsCollection<R> other) {
        instantiateFrom(other);
    }

    public <R extends Requirement<?, R>> void fulfilFrom(FulfilledRequirement<?, R> fulfilledRequirement) {
        instantiateFrom(fulfilledRequirement);
    }

    public void absorb(ProvisionsComponents other) {
        for (FulfilledRequirementsCollection<?> frp : other.componentProvisions().values()) {
            absorb(frp);
        }
    }

    public <R extends Requirement<?, R>> boolean alreadySatisfies(FulfilledRequirement<?, R> fulfilledRequirement) {
        ComponentType componentType = fulfilledRequirement.componentType();
        R comparable = fulfilledRequirement.getComparable();
        Class<R> comparableClass = (Class<R>) comparable.getClass();
        return componentProvisions.containsKey(componentType) && componentProvisions.get(componentType)
                                                                                    .fulfilledRequirements()
                                                                                    .stream()
                                                                                    .filter(f -> f.contentClass()
                                                                                                  .isAssignableFrom(fulfilledRequirement.contentClass()))
                                                                                    .filter(f -> comparableClass.isAssignableFrom(f.getComparable()
                                                                                                                                   .getClass()))
                                                                                    .anyMatch(f -> comparable.lt(comparableClass.cast(f.getComparable())));
    }

    public <R extends Requirement<?, R>> void absorb(FulfilledRequirementsCollection<R> frp) {
        for (FulfilledRequirement<?, R> fulfilledRequirement : frp.fulfilledRequirements()) {
            if (!alreadySatisfies(fulfilledRequirement)) provide(fulfilledRequirement);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Components {").append("\n");
        sb.append("Required Components:").append("\n");
        for (Table.Cell<ComponentType, Requirement<?, ?>, Container<?>> cell : componentRequirements().cellSet()) {
            Container<?> container = cell.getValue();
            sb.append("\t")
              .append(cell.getRowKey())
              .append(" - ")
              .append(cell.getColumnKey())
              .append(" - ")
              .append(container.isNonEmpty() ? container : "not fulfilled")
              .append("\n");
        }
        sb.append("Exposed Components:").append("\n");
        for (FulfilledRequirementsCollection<?> frp : componentProvisions().values()) {
            for (FulfilledRequirement<?, ?> f : frp.fulfilledRequirements()) {
                sb.append("\t").append(f).append("\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Table<ComponentType, Requirement<?, ?>, Container<?>> componentRequirements() {
        return componentRequirements;
    }

    @Override
    public Map<ComponentType, FulfilledRequirementsCollection<?>> componentProvisions() {
        return componentProvisions;
    }
}