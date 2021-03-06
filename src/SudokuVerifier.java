
public class SudokuVerifier {
	
	private String[] globalRows;
	public String[] getGlobalRows() {
		return this.globalRows;
	}

	private String[] globalColumns;
	public String[] getGlobalColumns() {
		return globalColumns;
	}

	private String[] subgrids;	
	public String[] getSubgrids() {
		return subgrids;
	}

	public int verify(String candidateSolution) {
		this.globalRows = new String[9];
		this.globalColumns = new String[9];
		this.subgrids = new String[9];
		
		// returns 0 if the candidate solution is correct
		
		//check that candidate solution has exactly 9*9 chars
		if (checkCandidateSolutionLength(candidateSolution) == false) {
			return -1;
		}
		
		if (checkCandidateForInvalidChars(candidateSolution) == false) {
			return -1;
		}
		
		
		//split candidate string to global rows -> string array
		this.globalRows = splitCandidateToGlobalRows(candidateSolution);
		
		//use global rows to create global columns -> string array
		this.globalColumns = createGlobalColumnsFromGlobalRows(this.globalRows);
		
		//use candidate string to create subgrids -> string array
		this.subgrids = createSubgridsFromGlobalRows(this.globalRows);
		
		if (checkSudokuArray(this.globalRows) != 0) {
			return -3;
		}
		
		if (checkSudokuArray(this.globalColumns) != 0) {
			return -4;
		}
		
		if (checkSudokuArray(this.subgrids) != 0) {
			return -2;
		}
		
		return 0;
		
		//check
	}
	
	private String[] splitCandidateToGlobalRows(String candidateSolution) {
		String[] globalRows = new String[9];
		String tempString;
		
		for(int i = 0; i < 9; i++) {
			if(i == 0) tempString = candidateSolution.substring(i, 9);
			else tempString = candidateSolution.substring(i * 9, i * 9 + 9);
			globalRows[i] = tempString;
			tempString = "";
		}

		return globalRows;
	}
	
	private String[] createGlobalColumnsFromGlobalRows(String[] globalRows) {
		String[] globalColumns = new String[9];
		StringBuilder tempString = new StringBuilder();
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				tempString.append(globalRows[j].charAt(i));
			}
			globalColumns[i] = tempString.toString();
			tempString.setLength(0);
		}

		return globalColumns;
	}
	
	private String[] createSubgridsFromGlobalRows(String[] globalRows) {
		String[] subgrids = new String[9];
		StringBuilder tempString = new StringBuilder();
		
		int currentRow = 0;
		int currentColumn = 0;
		int subgridCounter = 0;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					tempString.append(getThreeCharsFromRow(globalRows[currentRow], currentColumn, currentColumn + 2));
					currentRow++;
				}
				subgrids[subgridCounter] = tempString.toString();
				subgridCounter++;
				tempString.setLength(0);
			}
			currentRow = 0;
			currentColumn += 3;
		}
		
		//Implement
		return subgrids;
	}
	
	private int checkSudokuArray(String[] sudokuArray) {
		for(int i = 0; i < 9; i++) {
			for(int j = 1; i < 10; j++) {
				int value = Character.digit(sudokuArray[i].charAt(j - 1), 10);
				if (value == j) break;
				return -1;
			}
		}
		return 0;
	}
	
	private boolean checkCandidateForInvalidChars(String candidateSolution) {
		if (candidateSolution.matches("[0-9]+")) {
			return true;
		}
		else return false;
	}
	
	private boolean checkCandidateSolutionLength(String candidateSolution) {
		if (candidateSolution.length() != 81) {
			return false;
		}
		else return true;
	}
	
	private String getThreeCharsFromRow(String row, int begin, int end) {
		return row.substring(begin,end + 1);
	}
}
