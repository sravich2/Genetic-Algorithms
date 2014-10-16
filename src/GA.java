import java.util.Arrays;


public class GA {

	public static final double EXPECTED_VALUE = 19.5;			//The required value of arithmetic expression
	public static final int BIT_STRING_LENGTH = 9;				//Number of operators + numbers in the arithmetic expression (must be odd)
	public static final int POPULATION = 10;					//Size of initial generated population
	public static String[] digitsBinary = new String[10];		//Binary representation of 0-9
	public static String[] operatorsBinary = new String[4];		//Binary representatino of +, -. * and /
	public static String[] bitStrings = new String[POPULATION];	//The actual population
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		
		final double CROSSOVER_RATE = 0.7;							
		final double MUTATION_RATE = 0.001*GA.BIT_STRING_LENGTH;	//Probability increases with length of bit strings	
		
		double[] fitnessScores = new double[POPULATION];			//Fitness scores of the population
		
		//Initializing bit strings
		for (int i = 0;i<POPULATION;i++)
			bitStrings[i] = "";
		
		//Assigning binary representation of 0-9 to digitsBinary[]
		for (int i = 0;i<10;i++)
			digitsBinary[i] = StringOperations.StringLeftPadding(Integer.toBinaryString(i), 4);
		
		//Assigning binary representations of +, -, * and / (arbitrary selection)
		operatorsBinary[0] = "1010";	//Add
		operatorsBinary[1] = "1011";	//Subtract
		operatorsBinary[2] = "1100";	//Multiply
		operatorsBinary[3] = "1101";	//Divide
		
		//Generating a population of random bit strings
		boolean previousWasOperator = true;
		for (int j = 0;j<POPULATION;j++)
		{
			bitStrings[j] = generateBitString();
			fitnessScores[j] = BitStringProperties.fitnessScore(bitStrings[j]);
			if (fitnessScores[j] == 1/(Math.abs(GA.EXPECTED_VALUE)))
			{
				bitStrings[j] = generateBitString();
				fitnessScores[j] = BitStringProperties.fitnessScore(bitStrings[j]);
				//System.out.println("Switched it");
			}
		}
		
		//Performing crossovers and mutations until the desired result is obtained
		outerloop:
		while (true)
		{
			int indexOfFirstChromosome = BitStringProperties.indexOfRouletteWheelSelection(fitnessScores);
			int indexOfSecondChromosome = BitStringProperties.indexOfRouletteWheelSelection(fitnessScores);
			//Crossover
			if (Math.random()<CROSSOVER_RATE)
			{
				//System.out.println("Crossing over...");
				bitStrings[indexOfFirstChromosome] = StringOperations.swap(bitStrings[indexOfFirstChromosome],bitStrings[indexOfSecondChromosome], (int)(Math.random()*GA.BIT_STRING_LENGTH))[0];
				bitStrings[indexOfSecondChromosome] = StringOperations.swap(bitStrings[indexOfFirstChromosome],bitStrings[indexOfSecondChromosome], (int)(Math.random()*GA.BIT_STRING_LENGTH))[1];
				fitnessScores[indexOfFirstChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfFirstChromosome]);
				fitnessScores[indexOfSecondChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfSecondChromosome]);
			}
			//Mutation
			if (Math.random() < MUTATION_RATE)
			{
				//System.out.println("Mutating...");
				if (Math.random()<0.5)
				{
					bitStrings[indexOfFirstChromosome] = StringOperations.flip(bitStrings[indexOfFirstChromosome], (int)(Math.random()*GA.BIT_STRING_LENGTH));
					fitnessScores[indexOfFirstChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfFirstChromosome]);
				}
				else
				{
					bitStrings[indexOfSecondChromosome] = StringOperations.flip(bitStrings[indexOfSecondChromosome], (int)(Math.random()*GA.BIT_STRING_LENGTH));
					fitnessScores[indexOfSecondChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfSecondChromosome]);
				}
				
			}
			//System.out.println(fitnessScores[indexOfFirstChromosome]);
			//System.out.println(fitnessScores[indexOfSecondChromosome]);
			
			//Discard bit strings with a fitness score of 0
			if (fitnessScores[indexOfFirstChromosome] == 1/(Math.abs(GA.EXPECTED_VALUE)))
			{
				
				bitStrings[indexOfFirstChromosome] = generateBitString();
				fitnessScores[indexOfFirstChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfFirstChromosome]);
				//System.out.println(fitnessScores[indexOfFirstChromosome]);
			}
			if (fitnessScores[indexOfSecondChromosome] == 1/(Math.abs(GA.EXPECTED_VALUE)))
			{
				
				bitStrings[indexOfSecondChromosome] = generateBitString();
				fitnessScores[indexOfSecondChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfSecondChromosome]);
				//System.out.println(fitnessScores[indexOfSecondChromosome]);
			}
			
			//Discard one of two bit strings with identical fitness scores
			if (fitnessScores[indexOfSecondChromosome] == fitnessScores[indexOfFirstChromosome])
			{
				bitStrings[indexOfSecondChromosome] = generateBitString();
				fitnessScores[indexOfSecondChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfSecondChromosome]);
				//System.out.println(fitnessScores[indexOfSecondChromosome]);
			}

			//Found solution!
			if (fitnessScores[indexOfFirstChromosome] == 0)
			{
				//System.out.println(fitnessScores[indexOfFirstChromosome]);
				System.out.println(bitStrings[indexOfFirstChromosome]);
				System.out.println(BitStringProperties.bitStringToString(bitStrings[indexOfFirstChromosome]));
				break outerloop;
			}
			if (fitnessScores[indexOfSecondChromosome] == 0)
			{
				//System.out.println(fitnessScores[indexOfSecondChromosome]);
				System.out.println(bitStrings[indexOfSecondChromosome]);
				System.out.println(BitStringProperties.bitStringToString(bitStrings[indexOfSecondChromosome]));
				break outerloop;
			}

		}
		
		
		}
		
		
	public static String generateBitString()
	{
		boolean previousWasOperator = true;
		String output = "";
		previousWasOperator = true;
		
		for (int k = 0;k<GA.BIT_STRING_LENGTH;k++)
			{
				if (previousWasOperator)
				{
					output += digitsBinary[(int)(Math.random()*10)];
					previousWasOperator = false;
				}
				else
				{
					output += operatorsBinary[(int)(Math.random()*4)];
					previousWasOperator = true;
				}
				
		}
		return output;
		
	}	
}

