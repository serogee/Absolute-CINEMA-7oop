package theater;

import java.util.Set;

import show.utils.ExperienceType;

public class StandardTheater extends Theater {

    private static final Set<ExperienceType> SUPPORTED_EXPERIENCE_TYPES = Set.of(ExperienceType.STANDARD_2D, ExperienceType.STANDARD_3D);
    
    public StandardTheater(String name, int nRow, int nColumn) {
        super(name, nRow, nColumn, "□", "■");
    }

    public void displayLayout(Set<String> reservedSeatIDs) {

    };
    
    public boolean supportsExperienceType(ExperienceType experiencetype) {
        return StandardTheater.SUPPORTED_EXPERIENCE_TYPES.contains(experiencetype);
    };
}
