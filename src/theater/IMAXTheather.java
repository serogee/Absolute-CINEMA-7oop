package theater;

import java.util.Set;

import show.utils.ExperienceType;
import theater.utils.SoundMode;

public class IMAXTheather extends Theater {

    private SoundMode soundMode;
    
    public IMAXTheather(String name, int nRow, int nColumn) {
        super(name, nRow, nColumn, "", "");
        this.soundMode = SoundMode.STANDARD;
    }

    public void displayLayout(Set<String> reservedSeatIDs) {

    };
    
    public boolean supportsExperienceType(ExperienceType experiencetype) { 
        return experiencetype == ExperienceType.IMAX; 
    };

    // Mutators
    public void setSoundMode(SoundMode soundMode) { this.soundMode = soundMode; }
}
