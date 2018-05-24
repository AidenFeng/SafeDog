import java.io.*;

import SuperDog.Dog;
import SuperDog.DogTime;
import SuperDog.DogStatus;
import SuperDog.DogApiVersion;

class dog_demo
{
    public static final int DEMO_MEMBUFFER_SIZE = 128;

    public static final String vendorCode = new String(
      "FORGITHUB");

    public static final String productScope = new String(
      "<dogscope>\n" +
      " <product id=\"12\"/>\n" +
      "</dogscope>\n");

    public static final String scope = new String(
      "<dogscope />\n");

    public static final String view = new String(
      "<dogformat root=\"my_custom_scope\">\n" +
      "  <dog>\n" +
      "    <attribute name=\"id\" />\n" +
      "    <attribute name=\"type\" />\n" +
      "    <feature>\n" +
      "      <attribute name=\"id\" />\n" +
      "      <element name=\"license\" />\n" +
      "      <session>\n" +
      "        <element name=\"hostname\" />\n" +
      "        <element name=\"apiversion\" />\n" +
      "      </session>\n" +
      "    </feature>\n" +
      "  </dog>\n" +
      "</dogformat>\n");

    public static final byte[] data = { 0x74, 0x65, 0x73, 0x74, 0x20, 0x73, 0x74, 0x72,
                                        0x69, 0x6e, 0x67, 0x20, 0x31, 0x32, 0x33, 0x34 };
    public static final String strdata = "test string 1234";

    private static DogTime datetime;

    /************************************************************************
     *
     * helper function: dumps a given block of data, in hex and ascii
     */

    /*
     * Converts a byte to hex digit and writes to the supplied buffer
     */
    private static void byte2hex(byte b, StringBuffer buf)
    {
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                            '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /*
     * Converts a byte array to hex string
     */
    private static String toHexString(byte[] block)
    {
        StringBuffer buf = new StringBuffer();

        int len = block.length;

        for (int i = 0; i < len; i++)
        {
            byte2hex(block[i], buf);
            if (i < len - 1)
            {
                buf.append(":");
            }
        }
        return buf.toString();
    }

    public static void dump(byte[] data, String margin)
    {
        int i, j;
        byte b;
        byte[] s = new byte[16];
        byte hex[] = {0};
        String shex;
        String PrtString;

        if (data.length == 0) return;

        s[0] = 0;
        j = 0;
        for (i = 0; i < data.length; i++)
        {
            if (j == 0) System.out.print(margin);
            b = data[i];
            if ((b < 32) || (b > 127)) s[j] = '.'; else s[j] = b;
            if (j < 15)
                s[j+1] = 0;
            hex[0] = b; shex = toHexString(hex);
            System.out.print(shex + " ");
            j++;
            if (((j & 3) == 0) && (j < 15)) System.out.print("| ");
            PrtString = new String(s);
            if (j > 15)
            {
                System.out.println("[" + PrtString + "]");
                j = 0;
                s[0] = 0;
            }
        }
        if (j != 0)
        {
            while (j < 16)
            {
                System.out.print("   ");
                j++;
                if (((j & 3) == 0) && (j < 15)) System.out.print("| ");
            }
            PrtString = new String(s);
            System.out.println(" [" + PrtString + "]");
        }
    }

//    public static void main(String argv[]) throws java.io.IOException
//    {
//       // Dog curDog = new Dog(Dog.DOG_DEFAULT_FID);
//       // curDog.login(vendorCode);
//    }
}
