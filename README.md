#Jet Brains Courses Challenges Solutions

The solutions for challenges of Jet Brains courses...

##Stage #1: Blockchain

###Description
Blockchain has a simple interpretation: it's just a chain of blocks. It represents a sequence of data that you can't break in the middle; you can only append new data at the end of it. All the blocks in the blockchain are chained together.

Check out this great video about the blockchain. It uses a different approach to reach the final result of the project, which is cryptocurrencies, but it explains the blockchain pretty well.

To be called a blockchain, every block must include the hash of the previous block. Other fields of the block are optional and can store various information. The hash of a block is a hash of all fields of a block. So, you can just create a string containing every element of a block and then get the hash of this string.

Note that if you change one block in the middle, the hash of this block will also change. and the next block in the chain would no longer contain the hash of the previous block. Therefore, it’s easy to check that the chain is invalid.

In the first stage, you need to implement such a blockchain. In addition to storing the hash of the previous block, every block should also have a unique identifier. The chain starts with a block whose id = 1. Also, every block should contain a timestamp representing the time the block was created. You can use the following code to get such a timestamp. This represents the number of milliseconds since 1 January 1970.

long timeStamp = new Date().getTime(); // 1539795682545 represents 17.10.2018, 20:01:22.545 
By the way, since the first block doesn't have a previous one, its hash of the previous block should be 0.

The class Blockchain should have at least two methods: the first one generates a new block in the blockchain and the second one validates the blockchain and returns true if the blockchain is valid. Of course, the Blockchain should store all it's generated blocks. The validation function should validate all the blocks of this blockchain.

Also, for hashing blocks, you need to choose a good cryptographic hash function which is impossible to reverse-engineer. Insecure hash functions allow hackers to change the information of the block so that that the hash of the block stays the same, so the hash function must be secure. A good example of a secure hash function is SHA-256. You can use this implementation of the SHA-256 hashing:

import java.security.MessageDigest;

class StringUtil {
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
Try to create at least 10 blocks in this stage. After the creation, validate the created blockchain using your validation method.

Output example

The example below shows how your output might look. To be tested successfully, program should output information about first five blocks of the blockchain. Blocks should be separated by an empty line..

Block:
Id: 1
Timestamp: 1539810682545
Hash of the previous block: 
0
Hash of the block: 
796f0a5106c0e114cef3ee14b5d040ecf331dbf1281cef5a7b43976f5715160d

Block:
Id: 2
Timestamp: 1539810682557
Hash of the previous block: 
796f0a5106c0e114cef3ee14b5d040ecf331dbf1281cef5a7b43976f5715160d
Hash of the block: 
717242af079ccb7dd44c3f016936a81cf8ab2d4c1901243f30cbb7daa2060a0d

Block:
Id: 3
Timestamp: 1539810682558
Hash of the previous block: 
717242af079ccb7dd44c3f016936a81cf8ab2d4c1901243f30cbb7daa2060a0d
Hash of the block: 
28a2269bb34abd01dee9cea03400345bc9ea7322d73d3263221a47c6d970404f

##Stage #2: A proof of work concept

###Description
The security of our blockchain is pretty low. You can't just change some information in the middle of a blockchain, 
because the hash of this block will also be changed. And the next block still keeps the old hash value of the previous 
block. But can't we replace the old hash value with the new hash value so everything will be ok? No, because when you 
change the value of the previous hash in the block, the hash of this block will also be changed! To fix this, you need 
to change the value of the previous hash in the block after it. To solve this problem, you need to fix hash values in 
all the blocks until the last block of the blockchain!

This seems to be a pretty hard task to execute, doesn’t it? If the time it takes to fix the hash value of the previous 
block is less than time to create a new block, we suddenly would be fixing blocks faster than the system can create 
them and eventually we will fix them all. The problem is that fixing the hash values is easy to do. The blockchain 
becomes useless if it is possible to change information in it.

The solution to this is called proof of work. This means that creating new blocks and fixing hash values in the 
existing ones should take time and shouldn't be instant. The time should depend on the amount of computational work 
put into it. This way, the hacker must have more computational resources than the rest of the computers of the system 
put together.

The main goal is that the hash of the block shouldn't be random. It should start with some amount of zeros. To achieve 
that, the block should contain an additional field: a magic number. Of course, this number should take part in 
calculating the hash of this block. With one magic number, and with another, the hashes would be totally different even 
though the other part of the block stays the same. But with the help of probability theory, we can say that there exist 
some magic numbers, with which the hash of the block starts with some number of zeros. The only way to find one of them 
is to make random guesses until we found one of them. For a computer, this means that the only way to find the solution 
is to brute force it: try 1, 2, 3, and so on. The better solution would be to brute force with random numbers, not with 
the increasing from 1 to N where N is the solution. You can see this algorithm in the animation below: