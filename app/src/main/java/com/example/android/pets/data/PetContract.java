package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by KingMan on 08-Aug-17.
 */

public final class PetContract {

    private PetContract() {}

    public static class PetEntry implements BaseColumns{
        /** Name of database table for pets */
        public final static String TABLE_NAME = "pets";

        public final static String fpkey = BaseColumns._ID;

        public final static String COLUMN_PET_NAME ="fname";
        public final static String COLUMN_PET_BREED = "fbreed";
        public final static String COLUMN_PET_GENDER = "fgender";
        public final static String COLUMN_PET_WEIGHT = "fweight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }

}
