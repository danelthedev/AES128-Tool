package org.example;

public class AESEncryptor extends AES{

    private byte[][] sBox = {
            {(byte)0x63, (byte)0x7c, (byte)0x77, (byte)0x7b, (byte)0xf2, (byte)0x6b, (byte)0x6f, (byte)0xc5, (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2b, (byte)0xfe, (byte)0xd7, (byte)0xab, (byte)0x76},
            {(byte)0xca, (byte)0x82, (byte)0xc9, (byte)0x7d, (byte)0xfa, (byte)0x59, (byte)0x47, (byte)0xf0, (byte)0xad, (byte)0xd4, (byte)0xa2, (byte)0xaf, (byte)0x9c, (byte)0xa4, (byte)0x72, (byte)0xc0},
            {(byte)0xb7, (byte)0xfd, (byte)0x93, (byte)0x26, (byte)0x36, (byte)0x3f, (byte)0xf7, (byte)0xcc, (byte)0x34, (byte)0xa5, (byte)0xe5, (byte)0xf1, (byte)0x71, (byte)0xd8, (byte)0x31, (byte)0x15},
            {(byte)0x04, (byte)0xc7, (byte)0x23, (byte)0xc3, (byte)0x18, (byte)0x96, (byte)0x05, (byte)0x9a, (byte)0x07, (byte)0x12, (byte)0x80, (byte)0xe2, (byte)0xeb, (byte)0x27, (byte)0xb2, (byte)0x75},
            {(byte)0x09, (byte)0x83, (byte)0x2c, (byte)0x1a, (byte)0x1b, (byte)0x6e, (byte)0x5a, (byte)0xa0, (byte)0x52, (byte)0x3b, (byte)0xd6, (byte)0xb3, (byte)0x29, (byte)0xe3, (byte)0x2f, (byte)0x84},
            {(byte)0x53, (byte)0xd1, (byte)0x00, (byte)0xed, (byte)0x20, (byte)0xfc, (byte)0xb1, (byte)0x5b, (byte)0x6a, (byte)0xcb, (byte)0xbe, (byte)0x39, (byte)0x4a, (byte)0x4c, (byte)0x58, (byte)0xcf},
            {(byte)0xd0, (byte)0xef, (byte)0xaa, (byte)0xfb, (byte)0x43, (byte)0x4d, (byte)0x33, (byte)0x85, (byte)0x45, (byte)0xf9, (byte)0x02, (byte)0x7f, (byte)0x50, (byte)0x3c, (byte)0x9f, (byte)0xa8},
            {(byte)0x51, (byte)0xa3, (byte)0x40, (byte)0x8f, (byte)0x92, (byte)0x9d, (byte)0x38, (byte)0xf5, (byte)0xbc, (byte)0xb6, (byte)0xda, (byte)0x21, (byte)0x10, (byte)0xff, (byte)0xf3, (byte)0xd2},
            {(byte)0xcd, (byte)0x0c, (byte)0x13, (byte)0xec, (byte)0x5f, (byte)0x97, (byte)0x44, (byte)0x17, (byte)0xc4, (byte)0xa7, (byte)0x7e, (byte)0x3d, (byte)0x64, (byte)0x5d, (byte)0x19, (byte)0x73},
            {(byte)0x60, (byte)0x81, (byte)0x4f, (byte)0xdc, (byte)0x22, (byte)0x2a, (byte)0x90, (byte)0x88, (byte)0x46, (byte)0xee, (byte)0xb8, (byte)0x14, (byte)0xde, (byte)0x5e, (byte)0x0b, (byte)0xdb},
            {(byte)0xe0, (byte)0x32, (byte)0x3a, (byte)0x0a, (byte)0x49, (byte)0x06, (byte)0x24, (byte)0x5c, (byte)0xc2, (byte)0xd3, (byte)0xac, (byte)0x62, (byte)0x91, (byte)0x95, (byte)0xe4, (byte)0x79},
            {(byte)0xe7, (byte)0xc8, (byte)0x37, (byte)0x6d, (byte)0x8d, (byte)0xd5, (byte)0x4e, (byte)0xa9, (byte)0x6c, (byte)0x56, (byte)0xf4, (byte)0xea, (byte)0x65, (byte)0x7a, (byte)0xae, (byte)0x08},
            {(byte)0xba, (byte)0x78, (byte)0x25, (byte)0x2e, (byte)0x1c, (byte)0xa6, (byte)0xb4, (byte)0xc6, (byte)0xe8, (byte)0xdd, (byte)0x74, (byte)0x1f, (byte)0x4b, (byte)0xbd, (byte)0x8b, (byte)0x8a},
            {(byte)0x70, (byte)0x3e, (byte)0xb5, (byte)0x66, (byte)0x48, (byte)0x03, (byte)0xf6, (byte)0x0e, (byte)0x61, (byte)0x35, (byte)0x57, (byte)0xb9, (byte)0x86, (byte)0xc1, (byte)0x1d, (byte)0x9e},
            {(byte)0xe1, (byte)0xf8, (byte)0x98, (byte)0x11, (byte)0x69, (byte)0xd9, (byte)0x8e, (byte)0x94, (byte)0x9b, (byte)0x1e, (byte)0x87, (byte)0xe9, (byte)0xce, (byte)0x55, (byte)0x28, (byte)0xdf},
            {(byte)0x8c, (byte)0xa1, (byte)0x89, (byte)0x0d, (byte)0xbf, (byte)0xe6, (byte)0x42, (byte)0x68, (byte)0x41, (byte)0x99, (byte)0x2d, (byte)0x0f, (byte)0xb0, (byte)0x54, (byte)0xbb, (byte)0x16}
    };

    // public accessible functions

    public byte[] cipher(byte[] in, byte[] key) {
        // if the length of input array is not divisible by 16, add padding
        byte[] usedIn;
        if(in.length % 16 == 0) {
            usedIn = new byte[in.length];
            System.arraycopy(in, 0, usedIn, 0, in.length);
        }
        else{
            usedIn = new byte[(in.length / 16) * 16 + 16];
            System.arraycopy(in, 0, usedIn, 0, in.length);
        }

        // split input into blocks of 16 bytes, and encrypt each block then concatenate the results
        byte[][] blocks = new byte[usedIn.length / 16][16];

        for(int i = 0; i < usedIn.length; i += 16){
            byte[] block = new byte[16];
            for(int j = 0; j < 16; ++ j){
                block[j] = usedIn[i + j];
            }
            blocks[i / 16] = cipherBlock(block, key);
        }

        byte[] result = new byte[usedIn.length];

        for(int i = 0; i < blocks.length; ++ i){
            for(int j = 0; j < 16; ++ j){
                if(i * 16 + j < usedIn.length)
                    result[i * 16 + j] = blocks[i][j];
            }
        }

        return result;
    }

    public byte[] cipherBlock(byte[] in, byte[] key){
        // w din documentatie, calculat local in functie
        byte[][] expandedKey = keyExpansion(key);

        byte[][] state = new byte[4][4];
        // Copy input to state
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[j][i] = in[i * 4 + j];
            }
        }
        // add round key
        state = addRoundKey(state, expandedKey, 0);
        // iterate over number of rounds
        for (int round = 1; round < rounds; ++round) {
            state = subBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, expandedKey, round);
        }
        // final round
        state = subBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, expandedKey, rounds);

        return stateToByteArr(state);
    }

    // internal functions


    private byte[][] subBytes(byte[][] state) {
        byte[][] newState = new byte[4][4];
        for(int i = 0; i < 4; ++ i)
            for(int j = 0; j < 4; ++ j){
                newState[i][j] = sBox[(state[i][j] & 0xF0) >> 4][state[i][j] & 0x0F];
            }
        return newState;
    }

    private byte[][] shiftRows(byte[][] state) {
        byte[][] newState = new byte[4][4];
        for(int i = 0; i < 4; ++ i)
            for(int j = 0; j < 4; ++ j)
                newState[i][j] = state[i][j];

        // iterate over rows
        for(int i = 1; i < 4; ++ i){

            // repeat shift operations by the index of the row
            for(int j = 0; j < i; ++ j){

                // perform shift
                byte aux = newState[i][0];
                for(int m = 0; m < 3; ++m)
                    newState[i][m] = newState[i][m+1];
                newState[i][3] = aux;
            }
        }

        return newState;
    }

    private byte[][] mixColumns(byte[][] state) {
        byte[][] newState = new byte[4][4];

        for (int c = 0; c < 4; c++)
        {
            newState[0][c] = (byte)(multiplyBytes((byte)0x02, state[0][c]) ^ multiplyBytes((byte) 0x03, state[1][c]) ^ state[2][c] ^ state[3][c]);
            newState[1][c] = (byte)(state[0][c] ^ multiplyBytes((byte) 0x02, state[1][c]) ^ multiplyBytes((byte) 0x03, state[2][c]) ^ state[3][c]);
            newState[2][c] = (byte)(state[0][c] ^ state[1][c] ^ multiplyBytes((byte) 0x02, state[2][c]) ^ multiplyBytes((byte) 0x03, state[3][c]));
            newState[3][c] = (byte)(multiplyBytes((byte) 0x03, state[0][c]) ^ state[1][c] ^ state[2][c] ^ multiplyBytes((byte) 0x02, state[3][c]));
        }

        return newState;
    }



}
