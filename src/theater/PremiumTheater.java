package theater;

import java.util.Set;

import show.utils.ExperienceType;
import theater.utils.LightMode;
import theater.utils.SoundMode;

public class PremiumTheater extends Theater {

    private static final Set<ExperienceType> SUPPORTED_EXPERIENCE_TYPES = Set.of(ExperienceType.STANDARD_2D, ExperienceType.STANDARD_3D, ExperienceType.IMAX);
    private LightMode lightMode;
    private SoundMode soundMode;
    
    public PremiumTheater(String name, int nRow, int nColumn) {
        super(name, nRow, nColumn, "", "");
        this.lightMode = LightMode.BLACKOUT;
        this.soundMode = SoundMode.STANDARD;
    }

    public void displayLayout(Set<String> reservedSeatIDs) {

    };
    
    public boolean supportsExperienceType(ExperienceType experiencetype) {
        return PremiumTheater.SUPPORTED_EXPERIENCE_TYPES.contains(experiencetype);
    };

    // Mutators
    public void setLightMode(LightMode lightMode) { this.lightMode = lightMode; }

    public void setSoundMode(SoundMode soundMode) { this.soundMode = soundMode; }
}
