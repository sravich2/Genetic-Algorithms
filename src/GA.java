import java.util.Arrays;


public class GA {

	public static final double EXPECTED_VALUE = -9.8;
	public static final int BIT_STRING_LENGTH = 9;
	public static final int POPULATION = 10;
	public static String[] digitsBinary = new String[10];
	public static String[] operatorsBinary = new String[4];
	public static String[] bitStrings = new String[POPULATION];
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		
		final double CROSSOVER_RATE = 0.7;
		final double MUTATION_RATE = 0.001*GA.BIT_STRING_LENGTH;		
		
		double[] fitnessScores = new double[POPULATION];
		
		//Initializing bit strings
		for (int i = 0;i<POPULATION;i++)
		{
			bitStrings[i] = "";
		}
		
		for (int i = 0;i<10;i++)
		{
			digitsBinary[i] = StringOperations.StringLeftPadding(Integer.toBinaryString(i), 4);
		}
		
		operatorsBinary[0] = "1010";	//Add
		operatorsBinary[1] = "1011";	//Subtract
		operatorsBinary[2] = "1100";	//Multiply
		operatorsBinary[3] = "1101";	//Divide
		
		//Generating a preset number (POPULATION) of random bit strings
		boolean previousWasOperator = true;
		for (int j = 0;j<POPULATION;j++)
		{
			/*previousWasOperator = true;
			for (int k = 0;k<BIT_STRING_LENGTH;k++)
			{
				if (previousWasOperator)
				{
					bitStrings[j] += digitsBinary[(int)(Math.random()*10)];
					previousWasOperator = false;
				}
				else
				{
					bitStrings[j] += operatorsBinary[(int)(Math.random()*4)];
					previousWasOperator = true;
				}
				
			}*/
			bitStrings[j] = generateBitString();
			fitnessScores[j] = BitStringProperties.fitnessScore(bitStrings[j]);
			if (fitnessScores[j] == 1/(Math.abs(GA.EXPECTED_VALUE)))
			{
				bitStrings[j] = generateBitString();
				fitnessScores[j] = BitStringProperties.fitnessScore(bitStrings[j]);
				//System.out.println("Switched it");
			}
		}
		
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
			if (fitnessScores[indexOfSecondChromosome] == fitnessScores[indexOfFirstChromosome])
			{
				bitStrings[indexOfSecondChromosome] = generateBitString();
				fitnessScores[indexOfSecondChromosome] = BitStringProperties.fitnessScore(bitStrings[indexOfSecondChromosome]);
				//System.out.println(fitnessScores[indexOfSecondChromosome]);
			}

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

