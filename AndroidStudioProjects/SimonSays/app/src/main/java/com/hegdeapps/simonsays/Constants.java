package com.hegdeapps.simonsays;

class Constants {

	public static final int WORDS = 1;
	public static final String FACE_GAME_MAX_SCORE ="FaceActivityMaximumScore" ;
    public static final int CORRECT_SOUND = 1;
    public static final String SHAPE_GAME_MAX_SCORE = "ShapeActivityMaximumScore";
    public static final String PUZZLE_GAME_MAX_SCORE ="PuzzleActivityMaximumScore" ;
    public static final String PUZZLE_GAME_MAX_LEVEL = "PuzzleActivityMaximumLevel";
    public static final String SIMON_GAME_MAX_SCORE ="SimonActivityMaximumScore" ;
    public static final boolean WORDPUZZLE = false;
	public static int WORD_LEVEL = 0;
	public static int NUMBER_LEVEL = 0;
	public static final boolean PICTUREPUZZLE = false;
	protected static int MAX_LEVELS = 4;// actually it is 4. But we count from 0
	public static final String admob_publisher_id = "a150cabf1100e53";
	public static final String PLAY_INSTRUCTIONS = "<html><body>You can play the game with words, numbers or cards. </p>"
			+ "If you select words or numbers, you are shown a set of words or numbers  for a few seconds. "
			+ " In next screen, you should tap these words/numbers in the correct order. Each correct answer will add a score of 100. The game stops with a wrong answer. </p>"
			+ "</p> To play cards, you should tap two cards, to see their faces. If they match they are cleared. If they don't "
			+ "they are turned back again. Each correct set gives you a score of 100</body></html>";
	public static final int MODE_NUMBER_QUIZ = 2;
	public static final int MODE_WORD_QUIZ = 4;
	protected static final int RESULT_LEVEL_FAILED = 200;
	protected static final int RESULT_LEVEL_CLEARED = 100;
	public static final int LOST_GAME = 100;
	public static final int GAME_WON_DELAY = 200;
	public static final int WORD_SHOW_DELAY = 300;
	public static final String PLAY_CLASSIC_INSTRUCTIONS = "<html><body>Click on two cards to see their faces.</p>. If they are similar, cards are cleared and you get 100 points.</p>If they are different cards are turned reverse again. </p> For smaller screens, vertical scrolling may be needed.</body></html>";
	public static final String PLAY_NUMBER_INSTRUCTIONS = "<html><body>Read the numbers in first screen.</p> Tap the same numbers in <b>same order</b> in next screen. Wrong number will end game</p> Correct answer will give you 100 points.</body></html> ";
	public static final String PLAY_WORD_INSTRUCTIONS = "<html><body>Read the words in first screen. </p>Tap the same words in <b>same order</b> in next screen.</p> Wrong word will end game</p>Correct answer will give you 100 points.</body></html>";

    public static final int WRONG_SOUND=2;


}
