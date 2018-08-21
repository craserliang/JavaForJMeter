package Util;

public class UniqueStringGenerator {
	
	private static final int MAX_GENERATE_COUNT = 99999;

    private static int generateCount = 0;
	
	private UniqueStringGenerator() {}

    public static synchronized String getUniqueString()

    {

        if(generateCount > MAX_GENERATE_COUNT)

            generateCount = 0;

        String uniqueNumber = Long.toString(System.currentTimeMillis()) + Integer.toString(generateCount);

        generateCount++;

        return uniqueNumber;

    }

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		for(int i=0;i<100;i++){
//		System.out.println(UniqueStringGenerator.getUniqueString());
//		}
//	}

}
