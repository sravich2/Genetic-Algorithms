/**
 * 
 * @author Sachin
 * This classes contains methods that manipulate strings
 */
public class StringOperations {

	/**
	 * @param x						Any input string
	 * @param finalLengthOfString	The length to which given string is to be padded from the left, with 0s
	 * @return paddedString 		Padded string
	 */
	public static String StringLeftPadding(String x, int finalLengthOfString)
	{
		String paddedString = "";
		if (x.length()>= finalLengthOfString)
			return x;
		else
		{
			for (int i = 0;i<finalLengthOfString-x.length();i++)
			{
				paddedString += "0";
			}
			paddedString += x;
			return paddedString;
		}
	}
	/**
	 * @param a			Any bit string
	 * @param b			Any bit string
	 * @return output	The bit string obtained after ORing the input bit strings
	 */
	public static String bitwiseOr(String a, String b)
	{
		String output = "";
		a = StringOperations.StringLeftPadding(a, Math.max(a.length(), b.length()));
		b = StringOperations.StringLeftPadding(b, Math.max(a.length(), b.length()));
		
		
		for (int i = 0;i<a.length();i++)
		{
			output += Math.max(Integer.parseInt((a.substring(i, i+1))), Integer.parseInt((b.substring(i, i+1))));
		}
		
		return output;
	}
	/**
	 * @param a			Any bit string
	 * @param b			Any bit string
	 * @return output	The bit string obtained after ANDing the input bit strings
	 */
	public static String bitwiseAnd(String a, String b)
	{
		a = StringLeftPadding(a, Math.max(a.length(), b.length()));
		b = StringLeftPadding(b, Math.max(a.length(), b.length()));
		
		String output = "";
		
		for (int i = 0;i<Math.min(a.length(), b.length());i++)
		{
			output += Math.min(Integer.parseInt((a.substring(i, i+1))), Integer.parseInt((b.substring(i, i+1))));
		}
		for (int j = Math.min(a.length(), b.length());j<Math.max(a.length(), b.length());j++)
		{
			if (a.length()<b.length())
			{
				output += b.substring(j, j+1);
			}
			else
				output += a.substring(j, j+1);
		}
		return output;
	}
	/**
	 * @param inputBitString	Any bit string
	 * @param n 				Index of bit to be flipped
	 * @return output			Bit string with bit at n flipped
	 */
	public static String flip(String a, int n)
	{
		String output = "";
		
		for (int i = 0;i<a.length();i++)
		{
			if (i == n)
			{
				if (a.substring(i, i+1).equals("0"))
					output += "1";
				else
					output += "0";
			}
			else
				output += a.substring(i, i+1);
		}
		return output;
	}
	
	//Swaps bit values of a and b from index n to the end of the bit strings
	/**
	 * @param a			Any bit string
	 * @param b			Any bit string
	 * @param n			The index starting from which bits are to swapped
	 * @return output	The bit string obtained after swapping all bits of the two input bit strings starting from n to the end of the bit strings	
	 */
	public static String[] swap(String a, String b, int n)
	{
		String[] output = new String[2];
		
		String tempA = StringLeftPadding(a, Math.max(a.length(), b.length()));
		String tempB = StringLeftPadding(b, Math.max(a.length(), b.length()));
		a = tempA.substring(0, n);
		b = tempB.substring(0, n);
		
		for (int i = n;i<tempA.length();i++)
		{
			a+= tempB.substring(i, i+1);
			b+= tempA.substring(i, i+1);
		}
		output[0] = a;
		output[1] = b;
		return output;
	}
	

	
}
