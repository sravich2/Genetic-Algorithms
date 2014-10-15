import java.util.Arrays;


public class BitStringProperties {
	
	public static double fitnessScore(String inputBitString)
	{
		double expectedResult = GA.EXPECTED_VALUE;
		double calculatedResult = ParseBitStringToResult(inputBitString);
		if (expectedResult-calculatedResult == 0)
			{
				return 0;
			}
		return (1/Math.abs((expectedResult-calculatedResult)));
	}
	
	public static double ParseBitStringToResult(String inputBitString)
	{
		String temp = "";
		String copyOfInputBitString = inputBitString;
		double result = 0;
		boolean previousWasOperator = true;
		int[] numbersInBitString = new int[(GA.BIT_STRING_LENGTH/2)+1];
		String[] operatorsInBitString = new String[GA.BIT_STRING_LENGTH];
		for (int k = 0;k<operatorsInBitString.length;k++)
		{
			operatorsInBitString[k] = "";
		}
		
		//Stores numbers in bit string to an integer array
		
		for (int j = 0;j<numbersInBitString.length-1;j++)
		{
			inputBitString = inputBitString.substring(4);
			temp = inputBitString.substring(0,4);
			switch (temp)
			{
				case "1010": operatorsInBitString[j] = "+";
				break;
				case "1011": operatorsInBitString[j] = "-";
				break;
				case "1100": operatorsInBitString[j] = "*";
				break;
				case "1101": operatorsInBitString[j] = "/";
				break;
			}
			//System.out.println(operatorsInBitString[j]);
			inputBitString = inputBitString.substring(4);
		}
		
		for (int i = 0;i<numbersInBitString.length;i++)
		{
				temp = copyOfInputBitString.substring(0, 4);
				if (temp.equals("1110") || temp.equals("1111"))
				{
					operatorsInBitString[i] = "";
				}
				else if ((Integer.parseInt(temp,2) < 10)) 
				{
				numbersInBitString[i] = Integer.parseInt(temp, 2);
				if (i != numbersInBitString.length-1)
					copyOfInputBitString = copyOfInputBitString.substring(8);
				//System.out.println(numbersInBitString[i]);
				}
				else
					operatorsInBitString[i] = "";
		}
		
		for (int l = 0;l<GA.BIT_STRING_LENGTH/2;l++)
		{
			if (operatorsInBitString[l].equals("+"))
			{
				if (l==0)
					result = numbersInBitString[l]+numbersInBitString[l+1];
				else
					result += numbersInBitString[l+1];
			}
			if (operatorsInBitString[l].equals("-"))
			{
				if (l==0)
					result = numbersInBitString[l]-numbersInBitString[l+1];
				else
					result -= numbersInBitString[l+1];	
			}
			if (operatorsInBitString[l].equals("*"))
			{
				if (l==0)
					result = numbersInBitString[l]*numbersInBitString[l+1];
				else
					result *= numbersInBitString[l+1];
			}
			if (operatorsInBitString[l].equals("/"))
			{
				if (l==0)
				{
					if (numbersInBitString[l+1] == 0)
						result = 0;
					else
						result = (double)numbersInBitString[l]/numbersInBitString[l+1];
				}
				else
				{
					if (numbersInBitString[l+1] == 0)
						return 0;
					else
						result /= numbersInBitString[l+1];
				}
			}
		}
		return result;
	}
	
	public static int indexOfRouletteWheelSelection(double[] fitnessScores)
	{
		double sumOfFitnessScores = 0;
		for (int i = 0;i<fitnessScores.length;i++)
		{
			sumOfFitnessScores += fitnessScores[i];
		}
		double randomNumber = Math.random()*sumOfFitnessScores;
		
		double offset = 0;
		int i = GA.POPULATION-1;
		while (true)
		{
			while (i>=0)
			{
				if (randomNumber >= offset && randomNumber < (fitnessScores[i]+offset))
					return i;
				offset += fitnessScores[i];
				i--;
			}
		}
	}
	
	public static String bitStringToString(String inputBitString)
	{
		boolean previousWasOperator = true;
		String output = "";
		String temp = "";
		for (int i = 0;i<GA.BIT_STRING_LENGTH;i++)
		{
			if (previousWasOperator)
			{
				temp = inputBitString.substring(0, 4);
				output += String.valueOf(Integer.parseInt(temp, 2));
				output += " ";
				inputBitString = inputBitString.substring(4);
				previousWasOperator = false;
			}
			else
			{
				temp = inputBitString.substring(0, 4);
				if (temp.equals("1110") || temp.equals("1111"))
					output = "";
				else
				{
					switch (temp)
					{
						case "1010": output += "+ ";
						break;
						case "1011": output += "- ";
						break;
						case "1100": output += "* ";
						break;
						case "1101": output += "/ ";
						break;
					}
				}
				inputBitString = inputBitString.substring(4);
				previousWasOperator = true;
			}
		}
			return output;
	}
	
}
