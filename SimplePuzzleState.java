package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Arrays;
import java.util.Random;

public class SimplePuzzleState implements PuzzleState {

	private int[][] board;
	private int pathLength;
	private SimplePuzzleState parent;
	private Operation operation;
	
	public SimplePuzzleState() {
		super();
		this.board = null;
		this.pathLength = 0;
		this.parent = null;
		this.operation = null;
	}
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		// TODO Auto-generated method stub
		board = new int[dimension][dimension];
		int count = 1;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				board[i][j] = count;
				count++;
				if (i == dimension - 1 && j > dimension - numberOfEmptySlots - 1) {
					board[i][j] = 0;
				}
			}
		}
	}

	@Override
	public int getValue(int row, int column) {
		// TODO Auto-generated method stub
		if (row >= 0 && row < board.length && column >= 0 && column < board.length) {
			return board[row][column];
		}
		return -1;
	}

	@Override
	public PuzzleState getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		return operation;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return pathLength;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		// TODO Auto-generated method stub
		if (getValue(row, column) == 0) {  //this.getValue?
			return null;
		}
		SimplePuzzleState state = new SimplePuzzleState();
		state.board = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				state.board[i][j] = this.getValue(i, j); // this.getValue?
			}
		}
		switch (op) {
			
			case MOVELEFT:
				if (column - 1 >= 0) {
					if (isEmpty(row, column - 1)) {
						int temp = state.board[row][column];
						state.parent = this;
						state.board[row][column] = 0;
						state.board[row][column - 1] = temp;
						state.pathLength = pathLength + 1;
						state.operation = op;
					}
					else {
						return null;
					}
				}
				else {
					return null;
				}
				break;
				
			case MOVERIGHT: 
				if (column + 1 < board.length) {
					if (isEmpty(row, column + 1)) {
						int temp = state.board[row][column];
						state.parent = this;
						state.board[row][column] = 0;
						state.board[row][column + 1] = temp;
						state.pathLength = pathLength + 1;
						state.operation = op;
					}
					else {
						return null;
					}
				}
				else {
					return null;
				}
				break;
				
			case MOVEUP:
				if (row - 1 >= 0) {
					if (isEmpty(row - 1, column)) {
						int temp = state.board[row][column];
						state.parent = this;
						state.board[row][column] = 0;
						state.board[row - 1][column] = temp;
						state.pathLength = pathLength + 1;
						state.operation = op;
					}
					else {
						return null;
					}
				}
				else {
					return null;
				}
				break;
				
			case MOVEDOWN:
				if (row + 1 < board.length) {
					if(isEmpty(row + 1, column)) {
						int temp = state.board[row][column];
						state.parent = this;
						state.board[row][column] = 0;
						state.board[row + 1][column] = temp;
						state.pathLength = pathLength + 1;
						state.operation = op;
					}
					else {
						return null;
					}
					//break;
				}
				else {
					return null;
				}
				break;
		}
		return state;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		// TODO Auto-generated method stub
		if(isEmpty(startRow, startColumn)) {
			return null;
		}
		if(!isEmpty(endRow, endColumn)) {
			return null;
		}
		int rDiff = endRow - startRow;
		int cDiff = endColumn - startColumn;
		PuzzleState state = new SimplePuzzleState();
		state = this;	
			while (rDiff != 0 || cDiff != 0){
				if (isEmpty(startRow, startColumn - 1) && cDiff < 0) {
					state = state.move(startRow, startColumn, Operation.MOVELEFT);
					startColumn--;
					cDiff++;
				}
				if (isEmpty(startRow, startColumn + 1) && cDiff > 0) {
					state = state.move(startRow, startColumn, Operation.MOVERIGHT);
					startColumn++;
					cDiff--;
				}
				if (isEmpty(startRow - 1, startColumn) && rDiff < 0) {
					state = state.move(startRow,  startColumn,  Operation.MOVEUP);
					startRow--;
					rDiff++;
				}
				if (isEmpty(startRow + 1, startColumn) && rDiff > 0) {
					state = state.move(startRow,  startColumn,  Operation.MOVEDOWN);
					startRow++;
					rDiff--;
				}
				return state;
			} 
			return state;
		}

	private Operation randMove() {
		Operation move = null;
		Random generator = new Random();
		int randNum = generator.nextInt(4);
		
		switch(randNum) {
			case 0: 
				move = Operation.MOVELEFT;
				break;
			case 1:
				move = Operation.MOVERIGHT;
				break;
			case 2: 
				move = Operation.MOVEUP;
				break;
			case 3:
				move = Operation.MOVEDOWN;
				break;
			default:
				break;
		}
		return move;
	}
	
	private int[] randEmpty() {
		int[] temp = null;
		int[] temp1 = null;
		int[] temp2 = null;
		
		int count = 0;
		Random generator = new Random();
		int randNum = 0;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (isEmpty(i, j)) {
					if (count == 0) {
						temp = new int[2];
						temp[0] = i;
						temp[1] = j;
					}
					else if (count == 1) {
						temp1 = new int[2];
						temp1[0] = i;
						temp1[1] = j;
					}
					else if (count == 2) {
						temp2 = new int[2];
						temp2[0] = i;
						temp2[1] = j;
					}
					count++;
				}
			}
		}
		if (count == 1) {
			return temp;
		}
		else if (count == 2) {
			randNum = generator.nextInt(2);
		}
		else {
			randNum = generator.nextInt(3);
		}
		switch (randNum) {
		
		case 0:
			return temp;
		case 1:
			return temp1;
		case 2: 
			return temp2;
		default:
			break;
		}
		return null;
	}
	
	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		// TODO Auto-generated method stub
		int count = 0;
		SimplePuzzleState state = new SimplePuzzleState();
		state.board = new int[board.length][board.length];
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				state.board[i][j] = getValue(i, j);
			}
		}
		while(count < pathLength) {
			Operation move = state.randMove();
			int[] empty = state.randEmpty();
			int row = empty[0];
			int column = empty[1];
			switch (move) {
				case MOVELEFT:
					column++;
					break;
				case MOVERIGHT:
					column--;
					break;
				case MOVEUP:
					row++;
					break;
				case MOVEDOWN:
					row--;
					break;
				default:
					break;
			}
			if (state.getValue(row,  column) > 0) {
				state = (SimplePuzzleState) state.move(row,  column,  move);
				count++;
			}
		}
		return state;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		// TODO Auto-generated method stub
		if (row >= 0 && row < board.length && column >= 0 && column < board.length) {
			if (board[row][column] == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimplePuzzleState other = (SimplePuzzleState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		// TODO Auto-generated method stub
		SimplePuzzleState state = new SimplePuzzleState();
		return state;
	}

}
