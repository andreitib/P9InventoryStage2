package com.example.android.p9inventorystage2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.p9inventorystage2.data.InventoryContract.InventoryEntry;

/**
 * {@link InventoryCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of inventory data as its data source. This adapter knows
 * how to create list items for each row of inventory data in the {@link Cursor}.
 */
public class InventoryCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the Inventory data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current Inventory can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView amountTextView = (TextView) view.findViewById(R.id.quantity_value);
        Button sellButton = view.findViewById(R.id.sell_button);

        // Find the columns of Inventory attributes that we're interested in
        final int idColumnIndex = cursor.getInt(cursor.getColumnIndex(InventoryEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_NAME);
        int suppliernameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_NAME);
        int amountColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_AMOUNT);

        // Read the inventory attributes from the Cursor for the current inventory
        String inventoryName = cursor.getString(nameColumnIndex);
        String inventorySupplierName = cursor.getString(suppliernameColumnIndex);
        final int amountValue = cursor.getInt(amountColumnIndex);

        // If the inventory breed is empty string or null, then use some default text
        // that says "Unknown supplier", so the TextView isn't blank.
        if (TextUtils.isEmpty(inventorySupplierName)) {
            inventorySupplierName = context.getString(R.string.unknown_supplier);
        }

        // Update the TextViews with the attributes for the current inventory
        nameTextView.setText(inventoryName);
        summaryTextView.setText(inventorySupplierName);
        amountTextView.setText(String.valueOf(amountValue));

        //Decrease the quantity by   1 on each click
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri amountUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, idColumnIndex);
                ContentValues values = new ContentValues();
                values.put(InventoryEntry.COLUMN_AMOUNT, amountValue - 1);
                context.getContentResolver().update(amountUri, values, null, null);
            }
        });



    }
}
