package com.example.android.p9inventorystage2.data;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

        private InventoryContract() {}


        public static final String CONTENT_AUTHORITY = "com.example.android.p9inventorystage2";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


        public static final String PATH_INVENTORIES = "items";


        public static final class InventoryEntry implements BaseColumns {

            /** The content URI to access the product data in the provider */
            public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORIES);

            /**
             * The MIME type of the {@link #CONTENT_URI} for a list of products.
             */
            public static final String CONTENT_LIST_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORIES;

            /**
             * The MIME type of the {@link #CONTENT_URI} for a single product.
             */
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORIES;


            public final static String TABLE_NAME = "Inventory";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ITEM_NAME = "name";
        public final static String COLUMN_ITEM_PRICE = "price";
        public final static String COLUMN_AMOUNT = "items";
        public final static String COLUMN_SUPPLIER_NAME = "supplier";
        public final static String COLUMN_SUPPLIER_PHONE = "phonesupplier";


        public final static String COLUMN_SIZE = "size";


        /**
         * Possible values for the size of the clothes.
         */
        public static final String SIZE_SMALL = "S";
        public static final String SIZE_MEDIUM = "M";
        public static final String SIZE_LARGE = "L";
        public static final String SIZE_XLARGE = "XL";



            /**
             * Returns whether or not the given gender is {@link #SIZE_SMALL}, {@link #SIZE_MEDIUM},
             * {@link #SIZE_LARGE} or {@link #SIZE_XLARGE}.
             */
            public static boolean isValidSize(String size) {
                if (size == SIZE_SMALL || size == SIZE_MEDIUM || size == SIZE_LARGE || size == SIZE_XLARGE) {
                    return true;
                }
                return false;
            }
        }
    }

