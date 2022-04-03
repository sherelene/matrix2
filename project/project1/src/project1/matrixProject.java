package project1;
 
import java.util.Random;


public class matrixProject {

	public static void main(String[] args) {
		
		//sampleTest();
		sequentialRuns();	
		
	}
	

	/**
	 * Used for carrying out the algorithms with random matrices of size n 
	 */
	public static void sequentialRuns() {
		int runs = 2048;
		int tests = 10;
		int n;
		int realN = 0;
		long startTime, endTime;
		long timeClassic = 0;
		long timeDC = 0;
		long timeStrassen = 0;
		int[][] A, B;
		int [][] sample1, sample2, sample3;

		for (int i = 1; i < runs; i++) {
			n = i; 
			//n = (int) Math.pow(2, i);
			
			for (int j = 0; j < 1; j++) {
				int[][] C  = new int[n][n];
				A = generateMatrix(n);
				B = generateMatrix(n); 
				//displayMatrix(A, n);
				//displayMatrix(B, n);
				
				if (isPowerOfTwo(n) == false) {
					A = modifyMatrix(A, n);
					B = modifyMatrix(B, n);
					C = modifyMatrix(C, n);
				    realN = n;
				    n++;
				}
				else{
					realN = n;
				}
			
				
				startTime = System.nanoTime();
				sample1 = classic(A, B, A.length);
				endTime = System.nanoTime();
				timeClassic += endTime - startTime;	
				//System.out.println("Classic Matrix Multiplication:" + realN);
				//displayMatrix(sample1, sample1.length);

				startTime = System.nanoTime();
				sample2 = divideAndConquer(A, B, A.length);
				endTime = System.nanoTime();
				timeDC += endTime - startTime;
				//System.out.println("\nDivide and Conquer Matrix Multiplication:" + realN);
				//displayMatrix(sample2, sample2.length);

				startTime = System.nanoTime();
				sample3 = strassenAlgo(A, B, C, A.length);
				endTime = System.nanoTime();
				timeStrassen += endTime - startTime; 
				//System.out.println("\nStrassen Matrix Multiplication:" + realN);
				//displayMatrix(sample3, sample3.length);
			}

			timeClassic= timeClassic / tests;
			timeDC = timeDC / tests;
			timeStrassen = timeStrassen / tests;

			System.out.println("n=" + realN + "\nClassic: " + timeClassic
							+ " nanoseconds\nDivide and Conquer: " + timeDC
							+ " nanoseconds\nStrassen: " + timeStrassen + " nanoseconds\n");
			}		
		}

	
	
	/**
	 * Used for testing purposes, this function will act as sanity check 
	 */
	public static void sampleTest() {
		int n=4;
		int tests = 10;
		int [][] sample1, sample2, sample3;
		long startTime, endTime;
		long timeClassic = 0;
		long timeDC = 0;
		long timeStrassen = 0;
		int[][] C  = new int[n][n];
		
		
		//for n = 4
		 int[][] A = { { 2, 0, -1, 6},
				   { 3, 7, 8, 0 },
				   { -5, 1, 6, -2 },
				   { 8, 0, 2, 7}}; 
		
		int[][] B = { { 0, 1, 6, 3},
				   { -2, 8, 7, 1 },
				   { 2, 0, -1, 0 },
				 { 9, 1, 6, -2} }; 
		
		/*//for n = 3
		 * int[][] A = { { 2, 0, -1},
				   { 3, 7, 8},
				   { -5, 1, 6 },
				   }; 
		
		int[][] B = { { 0, 1, 6},
				   { -2, 8, 7, },
				   { 2, 0, -1 },
				  };*/
		
		/*//for n = 5
		 * int[][] A = { { 2, 0, -1, 6, 5},
				   { 3, 7, 8, 0, 5 },
				   { -5, 1, 6, -2, 5 },
				   { 8, 0, 2, 7, 5},
				   { 9, 1, 6, -2, 5}}; //{ 8, 0, 2, 7}
		
		int[][] B = { { 0, 1, 6, 3, 5},
				   { -2, 8, 7, 1, 5 },
				   { 2, 0, -1, 0, 5 },
				 { 9, 1, 6, -2, 5},
				   { 9, 1, 6, -2, 5}}; //{ 9, 1, 6, -2} */
		
		if (n % 2 != 0) {
			A = modifyMatrix(A, n);
			B = modifyMatrix(B, n);
			C = modifyMatrix(C, n);
			n++;
		}
		
		sample1 = classic(A, B, A.length);
		sample2 = divideAndConquer(A, B, B.length);
		sample3 = strassenAlgo(A, B, C, A.length);
		System.out.println("Classic Matrix Multiplication:");
		displayMatrix(sample1, n);
		System.out.println("\nDivide and Conquer Matrix Multiplication:");
		displayMatrix(sample2, n);
		System.out.println("\nStrassen Matrix Multiplication:");
		displayMatrix(sample3, n);
	
		for (int j = 0; j < tests; j++) {
			startTime = System.nanoTime();
			classic(A, B, A.length);
			endTime = System.nanoTime();
			timeClassic += endTime - startTime;

			startTime = System.nanoTime();
			divideAndConquer(A, B, A.length);
			endTime = System.nanoTime();
			timeDC += endTime - startTime;

			startTime = System.nanoTime();
			strassenAlgo(A, B, C, A.length);
			endTime = System.nanoTime();
			timeStrassen += endTime - startTime; 
		}
		
		timeClassic= timeClassic / tests;
		timeDC = timeDC / tests;
		timeStrassen = timeStrassen / tests;
		
		System.out.println("n=" + n + "\nClassic: " + timeClassic
						+ " nanoseconds\nDivide and Conquer: " + timeDC
						+ " nanoseconds\nStrassen: " + timeStrassen + " nanoseconds\n"); 
	}
	
	public static int[][] modifyMatrix(int[][] arr, int n) {
		int count = 0;
		count = changePowerOfTwo(n);
	  
		int[][] matrix = new int[arr.length+count][arr[0].length+count];

	    for (int row = 0; row < matrix.length; row++) {

	        for (int col = 0; col < matrix[0].length; col++) {

	            if(row < arr.length && col < arr[0].length)
	            {
	                matrix[row][col] = arr[row][col];
	            }
	            else
	            {
	                matrix[row][col] = 0;
	            }

	        }

	    }
	  
	    //System.out.println("Test run");
        //displayMatrix(matrix, matrix.length);

	    return matrix;
	}
	
	/**
	 * Checks if number is a power of two
	 * */
	public static boolean isPowerOfTwo(int n){
	 while(n%2==0){
	 n=n/2;
	 }
	 
	 if(n==1){
	 return true;
	 }else{
	 return false;
	 }
	}
	
	/**
	 * takes original n and gives back number needed
	 * to have the next power of two
	 * 
	 */
	public static int changePowerOfTwo(int number) {
		if (number == 1) {
			return 1;
		}else if (number == 2) {
			return 0;
		}else if(number < 4) {
			return 1;
		}else if (number < 8) {
			return (8 - number);
		}else if (number < 16) {
			return 16 - number;
		}else if (number < 32) {
			return 32 - number;
		}else if (number < 64) {
			return 64 - number;
		}else if (number < 128) {
			return 128-number;
		}else if (number < 256) {
			return 256 - number;
		}else if (number < 512) {
			return 512-number;
		}else if (number < 1024) {
			return 1024- number;
		}else if (number < 2048) {
			return 2048 - number;
		}else {
			return 0;
		}
	}
	
	/**
	 * Used for testing purposes, this method will display the matrix sent as an argument
	 */
	public static void displayMatrix(int[][] matrix, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%10d", matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Generates a matrix of size n x n, filled with random numbers between -100 and 100
	 */
	public static int[][] generateMatrix(int n) {
		int max = 100;
		int min = -100;
		Random r = new Random();
		int[][] matrix = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int randomNum = r.nextInt((max - min) + 1) + min;
				matrix[i][j] = randomNum;
			}
		}
		return matrix;
	}


	/**
	 * Performs classic matrix multiplication using 3 nested for loops
	 */
	public static int[][] classic(int[][] A, int[][] B, int n) {
		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = 0;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return C;
	}
	

	/**
	 * Performs divide and conquer matrix multiplication by recursively
	 * calling itself on smaller matrices made up of 1/4 of the original matrix
	 */
	public static int[][] divideAndConquer(int[][] A, int[][] B, int n) {
		int[][] C = new int[n][n];

		if (n == 1) {
			C[0][0] = A[0][0] * B[0][0];
			return C;
		} else {
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];
			
			/*divide matrix into 4 halves from A and B*/
			divideMatrix(A, A11, 0, 0); //top left
			divideMatrix(A, A12, 0, n / 2); //top right
			divideMatrix(A, A21, n / 2, 0); //bottom left
			divideMatrix(A, A22, n / 2, n / 2); //bottom right
			
			divideMatrix(B, B11, 0, 0); //top left
			divideMatrix(B, B12, 0, n / 2); //top right
			divideMatrix(B, B21, n / 2, 0); //bottom left
			divideMatrix(B, B22, n / 2, n / 2); //bottom right

			int[][] C11 = addMatrix(divideAndConquer(A11, B11, n / 2), divideAndConquer(A12, B21, n / 2), n / 2);
			int[][] C12 = addMatrix(divideAndConquer(A11, B12, n / 2), divideAndConquer(A12, B22, n / 2), n / 2);
			int[][] C21 = addMatrix(divideAndConquer(A21, B11, n / 2), divideAndConquer(A22, B21, n / 2), n / 2);
			int[][] C22 = addMatrix(divideAndConquer(A21, B12, n / 2), divideAndConquer(A22, B22, n / 2), n / 2);

			combineMatrix(C11, C, 0, 0);
			combineMatrix(C12, C, 0, n / 2);
			combineMatrix(C21, C, n / 2, 0);
			combineMatrix(C22, C, n / 2, n / 2); 
		}

		return C;
	}
	
	
	/**
	 * Creates a new matrix based off the quadrants of A and B
	 */
	private static void divideMatrix(int[][] initialMatrix,
			int[][] newMatrix, int startingRow, int startingColumn) {

		int currentColumn = startingColumn;
		for (int i = 0; i < newMatrix.length; i++) {
			for (int j = 0; j < newMatrix.length; j++) {
				newMatrix[i][j] = initialMatrix[startingRow][currentColumn++];
			}
			currentColumn = startingColumn;
			startingRow++;
		}
	}
	
	
	/**
	 * Adds two matrices together
	 */
	private static int[][] addMatrix(int[][] A, int[][] B, int n) {

		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + B[i][j];
			}
		}
		return C;
		
	}
	
	
	/**
	 * Creates a new matrix based off of added matrices
	 */
	private static void combineMatrix(int[][] initialMatrix,
			int[][] newMatrix, int startingRow, int startingColumn) {
	
		int currentColumn = startingColumn;

		for (int i = 0; i < initialMatrix.length; i++) {
			for (int j = 0; j < initialMatrix.length; j++) {
				newMatrix[startingRow][currentColumn++] = initialMatrix[i][j];
			}
			currentColumn = startingColumn;
			startingRow++;
		}
	}

	
	/**
	 * Creation of 7 new matrices with Strassen's algorithm 
	 */
	public static int[][] strassenAlgo(int[][] A, int[][] B, int[][] C,  int n) {
		if (n == 1) {
			C[0][0] = A[0][0] * B[0][0];
			return C;
		}else if (n == 2) {
			C[0][0] = (A[0][0] * B[0][0]) + (A[0][1] * B[1][0]);
			C[0][1] = (A[0][0] * B[0][1]) + (A[0][1] * B[1][1]);
			C[1][0] = (A[1][0] * B[0][0]) + (A[1][1] * B[1][0]);
			C[1][1] = (A[1][0] * B[0][1]) + (A[1][1] * B[1][1]);	
		} else {
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			int[][] P1 = new int[n / 2][n / 2]; 
			int[][] P2 = new int[n / 2][n / 2];
			int[][] P3 = new int[n / 2][n / 2];
			int[][] P4 = new int[n / 2][n / 2];
			int[][] P5 = new int[n / 2][n / 2];
			int[][] P6 = new int[n / 2][n / 2];
			int[][] P7 = new int[n / 2][n / 2];
			
			/*divide matrix 4 halves for A and B*/
			divideMatrix(A, A11, 0, 0); //top left
			divideMatrix(A, A12, 0, n / 2); //top right
			divideMatrix(A, A21, n / 2, 0); //bottom left
			divideMatrix(A, A22, n / 2, n / 2); //bottom right
			
			divideMatrix(B, B11, 0, 0); //top left
			divideMatrix(B, B12, 0, n / 2); //top right
			divideMatrix(B, B21, n / 2, 0); //bottom left
			divideMatrix(B, B22, n / 2, n / 2); //bottom right

			
			// P1 = A11(B12 - B22)
			strassenAlgo(A11, subtractMatrix(B12, B22, n / 2), P1, n / 2); 
			
			// P2 = (A11 + A12)B22
			strassenAlgo(addMatrix(A11, A12, n / 2), B22, P2, n / 2);	
			
			// P3 = (A21 + A22)B11
			strassenAlgo(addMatrix(A21, A22, n / 2), B11, P3, n / 2); 
			
			// P4 = A22(B21 - B11)
			strassenAlgo(A22, subtractMatrix(B21, B11, n / 2), P4, n / 2);
			
			// P5 = (A11 + A22)(B11 + B22)
			strassenAlgo(addMatrix(A11, A22, n / 2), addMatrix(B11, B22, n / 2), P5, n / 2);
			
			// P6 = (A11 - A22)(B21 + B22)
			strassenAlgo(subtractMatrix(A12, A22, n / 2), addMatrix(B21, B22, n / 2), P6, n / 2);
			
			// P7 = (A11 - A22)(B11 + B12)
			strassenAlgo(subtractMatrix(A11, A21, n / 2), addMatrix(B11, B12, n / 2), P7, n / 2);

			
			// C11 = -P2 + P4 + P5 + P6
			int[][] C11 = addMatrix(subtractMatrix(addMatrix(P5, P6, P6.length), P2, P2.length), P4, P4.length);
			// C12 = P1 + P2
			int[][] C12 = addMatrix(P1, P2, P2.length);
			// C21 = P3 + P4
			int[][] C21 = addMatrix(P3, P4, P4.length);
			// C22 = P1 - P3 + P5 - P7
			int[][] C22 = addMatrix(subtractMatrix(P1, P3, P3.length), subtractMatrix(P5, P7, P7.length), P7.length); 

			combineMatrix(C11, C, 0, 0);
			combineMatrix(C12, C, 0, n / 2);
			combineMatrix(C21, C, n / 2, 0);
			combineMatrix(C22, C, n / 2, n / 2);
		}
		return C;
	}
	
	
	/**
	 * Subtracts two matrices
	 */
	private static int[][] subtractMatrix(int[][] A, int[][] B, int n) {

		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - B[i][j];
			}
		}
		return C;
	}

	

}
