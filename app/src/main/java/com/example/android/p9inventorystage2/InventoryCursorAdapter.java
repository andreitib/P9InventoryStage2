package com.example.android.p9inventorystage2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
     * This method reduced product stock by 1
     * @param context - Activity context
     * @param mUri - Uri used to update the stock of a specific product in the ListView
     * @param amountValue - current stock of that specific product
     */
    private void updateAmount(Context context, Uri mUri, int amountValue) {
        if (amountValue >= 1) {
            amountValue = amountValue - 1;
        } else if (amountValue == 0) {
            Toast.makeText(context.getApplicationContext(),
                    R.string.no_stock_supply, Toast.LENGTH_SHORT).show();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(InventoryEntry.COLUMN_AMOUNT, amountValue);
        context.getContentResolver().update(mUri,contentValues, null, null);
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
        TextView priceTextView = (TextView) view.findViewById(R.id.item_priceme);
        TextView amountTextView = (TextView) view.findViewById(R.id.amount_value);
        Button sellButton = view.findViewById(R.id.sell_button);

        // Find the columns of Inventory attributes that we're interested in
        final int idColumnIndex = cursor.getInt(cursor.getColumnIndex(InventoryEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_PRICE);
        int amountColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_AMOUNT);

        // Read the inventory attributes from the Cursor for the current inventory
        String inventoryName = cursor.getString(nameColumnIndex);
        String inventoryPrice = cursor.getString(priceColumnIndex);
        final int amountValue = cursor.getInt(amountColumnIndex);


        // Update the TextViews with the attributes for the current inventory
        nameTextView.setText(inventoryName);
        priceTextView.setText(inventoryPrice);
        amountTextView.setText(String.valueOf(amountValue));

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, idColumnIndex);
                updateAmount(context, mUri, amountValue);
            }
        });
    }
}
