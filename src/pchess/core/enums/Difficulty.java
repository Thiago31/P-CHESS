package pchess.core.enums;

/**
 * Difficulty is a enum type to configure difficulty in prechess games.
 * Three difficulty levels are defined:
 * <ul>
 * <li>EASY</li>
 * <li>MEDIUM</li>
 * <li>HARD</li>
 * </ul>
 * It is used by {@class ComputerPlayer} in order to choose its movement:
 * <ol>
 * <li>In <em>EASY</em> mode, 50% of computer movement is done randomly and 50%
 * is done by computing best move using implemented artificial intelligence</li>
 * <li>In <em>MEDIUM</em> mode, 20% of computer movement is done randomly and 
 * 50% is done by computing best move using implemented artificial intelligence
 * </li>
 * <li>In <em>HARD</em> mode, all moves is done by computing best move</li>
 * </ol>
 */
public enum Difficulty {
    
    /**
     * Difficulty level is easy.
     */
    EASY(0.5),
    
    /**
     * Difficulty level is medium.
     */
    MEDIUM(0.2),
    
    /**
     * Difficulty level is hard.
     */
    HARD(0);
    
    /**
     * In difficulty level, defines percentage in which computer does
     * random movement.
     */
    private final double percent;
    
    /**
     * Constructor. Makes a Difficulty.
     * 
     * @param percent percentage in which computer does random movement.
     */
    private Difficulty(double percent){
        this.percent = percent;
    }
    
    /**
     * Returns percentage in which computer does random movement.
     * 
     * @return percentage for random movement. 
     */
    public double getPercent(){
        return percent;
    }
}
