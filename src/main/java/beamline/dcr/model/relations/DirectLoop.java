package beamline.dcr.model.relations;

import beamline.dcr.annotations.ExposedDcrPattern;
import beamline.dcr.model.DcrModel;
import beamline.dcr.model.UnionRelationSet;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import java.util.Set;

@ExposedDcrPattern(
        name = "DirectLoop",
        latticeLevel = 2
)
public class DirectLoop implements RelationPattern {

    private String type = "DirectLoop";

    @Override
    public void populateConstraint(UnionRelationSet unionRelationSet) {
        Set<Pair<String, String>> DFGRelations = unionRelationSet.getDFGRelations();

        for (Pair<String, String> relation : DFGRelations) {


            String sourceActivity = relation.getLeft();
            String targetActivity = relation.getRight();

            Pair<String, String> inverseRelation = new ImmutablePair<String, String>(targetActivity, sourceActivity);

            if (DFGRelations.contains(inverseRelation) &
                    !unionRelationSet.DCRRelationsContains(
                            Triple.of(targetActivity, sourceActivity, DcrModel.RELATION.DIRECTLOOP))
                    & sourceActivity != targetActivity) {

                unionRelationSet.addDcrRelation(Triple.of(sourceActivity, targetActivity, DcrModel.RELATION.DIRECTLOOP));
            }

        }

    }

    @Override
    public String getType() {
        return type;
    }

}
