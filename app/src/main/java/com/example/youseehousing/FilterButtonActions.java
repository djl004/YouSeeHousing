package com.example.youseehousing;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class FilterButtonActions extends AppCompatActivity {
    private final static String TAG = "FilterButton";
    private final static String STRING_TRUE = "true";
    private final static String STRING_FALSE = "false";
    private static final int MAX_RENT = 99999;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Filter button methods
     * @param activityFragmentOrigin
     */
    public static void setLogOut(ActivityFragmentOrigin activityFragmentOrigin, final Context mContext) {
        AlertDialog.Builder mBuilder;
        AlertDialog dialog;
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mBuilder.setTitle("Are you sure you want to log out?");

        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {

                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(myIntent);
                Log.d(TAG, "User Signed Out");

            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    public static void setSquareFtFilter(final ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        View mView;
        final Spinner mSpinner2;
        ArrayAdapter<String> adapter2;
        final Spinner mSpinner3;
        ArrayAdapter<String> adapter3;
        AlertDialog dialog;
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mView = activityFragmentOrigin.getLayoutInflater().inflate(R.layout.dialog_doublespinner, null);

        mBuilder.setTitle("Set Square Foot (Min/Max) Filter");

        //mSpinner2 is lower bound
        mSpinner2 = (Spinner) ((View) mView).findViewById(R.id.spinner1);
        adapter2 = new ArrayAdapter<String>(activityFragmentOrigin, android.R.layout.simple_spinner_item,
                activityFragmentOrigin.getResources().getStringArray(R.array.sizeList));
        mSpinner2.setAdapter(adapter2);

        //mSpinner3 is upper bound
        mSpinner3 = (Spinner) ((View) mView).findViewById(R.id.spinner2);
        adapter3 = new ArrayAdapter<String>(activityFragmentOrigin, android.R.layout.simple_spinner_item,
                activityFragmentOrigin.getResources().getStringArray(R.array.sizeList));
        mSpinner3.setAdapter(adapter3);



        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                String lowerBound = mSpinner2.getSelectedItem().toString();
                String upperBound = mSpinner3.getSelectedItem().toString();
                Log.i(TAG, "Square foot filter set with : " + lowerBound + " " + upperBound );
                // Call to DaFilter here
                DaFilter.getInstance().setSqftMin(lowerBound);
                DaFilter.getInstance().setSqftMax(upperBound);
                refreshList(activityFragmentOrigin);
            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    public static void setLeaseLengthFilter(final ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        View mView;
        final Spinner mSpinner;
        ArrayAdapter<String> adapter;
        AlertDialog dialog;
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mView = activityFragmentOrigin.getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        mBuilder.setTitle("Set Lease Duration Filter");
        mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(activityFragmentOrigin, android.R.layout.simple_spinner_item,
                activityFragmentOrigin.getResources().getStringArray(R.array.durationList));
        mSpinner.setAdapter(adapter);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){
                    // Call to DaFilter
                    String selectedItem = mSpinner.getSelectedItem().toString();
                    DaFilter.getInstance().setLeaseLength(selectedItem);

                    Toast.makeText(activityFragmentOrigin,
                            mSpinner.getSelectedItem().toString(),
                            Toast.LENGTH_LONG)
                            .show();

                    Log.i(TAG, "Lease length filter set : " + selectedItem);
                    refreshList(activityFragmentOrigin);
                }
            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    public static void setExtrasFilter(final ActivityFragmentOrigin activityFragmentOrigin) {
        final AlertDialog.Builder mBuilder;
        AlertDialog dialog;
        final String[] values = {" Washer/Dryer "," Furnished "," Parking "," Pets "};
        final Boolean[] bools = {false, false, false, false}; // Corresponds to above array
        final ArrayList itemsSelected = new ArrayList();
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mBuilder.setTitle("Filter by Included Amenities");
        mBuilder.setMultiChoiceItems(values, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected.add(selectedItemId);
                            try {
                                bools[selectedItemId] = true;
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Bad array index");
                            }

                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                            try {
                                bools[selectedItemId] = false;
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Bad array index");
                            }
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Your logic when OK button is clicked
                        Log.i(TAG, "Extras filter set!");
                        extrasFilterHelper(bools);
                        refreshList(activityFragmentOrigin);
                    }
                })
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    /**
     * Helper function to set DaFilter values.
     * @param bools
     */
    private static void extrasFilterHelper(Boolean[] bools) {
        try {
            // Set W/D
            if (bools[0] == true) {
                DaFilter.getInstance().setWasherDryer(STRING_TRUE);
            } else {
                DaFilter.getInstance().setWasherDryer(STRING_FALSE);
            }
            // Set Furnished
            if (bools[1] == true) {
                DaFilter.getInstance().setFurnished(STRING_TRUE);
            } else {
                DaFilter.getInstance().setFurnished(STRING_FALSE);
            }
            // Set Parking
            if (bools[2] == true) {
                DaFilter.getInstance().setParking(STRING_TRUE);
            } else {
                DaFilter.getInstance().setParking(STRING_FALSE);
            }
            // Set Pets
            if (bools[3] == true) {
                DaFilter.getInstance().setPets(STRING_TRUE);
            } else {
                DaFilter.getInstance().setPets(STRING_FALSE);
            }

        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
        Log.i(TAG, "W/D : " + bools[0]);
        Log.i(TAG, "Furnished : " + bools[1]);
        Log.i(TAG, "Parking : " + bools[2]);
        Log.i(TAG, "Pets : " + bools[3]);
    }

    public static void setBedsBathsFilter(final ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        View mView;
        final Spinner mSpinner2;
        ArrayAdapter<String> adapter2;
        final Spinner mSpinner3;
        ArrayAdapter<String> adapter3;
        AlertDialog dialog;
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mView = activityFragmentOrigin.getLayoutInflater().inflate(R.layout.dialog_doublespinner, null);
        mBuilder.setTitle("Set #Bed/#Bathroom Filter");

        mSpinner2 = (Spinner) ((View) mView).findViewById(R.id.spinner1);
        adapter2 = new ArrayAdapter<String>(activityFragmentOrigin, android.R.layout.simple_spinner_item,
                activityFragmentOrigin.getResources().getStringArray(R.array.bedList));
        mSpinner2.setAdapter(adapter2);

        mSpinner3 = (Spinner) ((View) mView).findViewById(R.id.spinner2);
        adapter3 = new ArrayAdapter<String>(activityFragmentOrigin, android.R.layout.simple_spinner_item,
                activityFragmentOrigin.getResources().getStringArray(R.array.bathroomList));
        mSpinner3.setAdapter(adapter3);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {

                String beds = mSpinner2.getSelectedItem().toString();
                String baths = mSpinner3.getSelectedItem().toString();
                DaFilter.getInstance().setBeds(beds);
                DaFilter.getInstance().setBaths(baths);
                Log.i(TAG, "Bed/Bath filter set : " + beds + " " + baths);
                refreshList(activityFragmentOrigin);
            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    public static void setPriceFilter(final ActivityFragmentOrigin activityFragmentOrigin) {
        final AlertDialog.Builder mBuilder;
        final View mView;
        AlertDialog dialog;
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mView = activityFragmentOrigin.getLayoutInflater().inflate(R.layout.dialog_minmax, null);
        final EditText minTextView = (EditText) mView.findViewById(R.id.dialog_price_min);
        final EditText maxTextView = (EditText) mView.findViewById(R.id.dialog_price_max);

        mBuilder.setTitle("Set Min/Max Price Filter");

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                ////When ok is clicked
                int min = 0;
                int max = MAX_RENT;
                boolean success = true;

                try {
                    min = Integer.parseInt(minTextView.getText().toString());
                    max = Integer.parseInt(maxTextView.getText().toString());
                }
                catch (NumberFormatException e) {
                    Log.e(TAG, "Error parsing min or max!");
                    e.printStackTrace();
                    success = false;
                }

                // Error checking
                if(min > max) {
                    // Min must be less than max
                    Log.e(TAG, "Min > Max :" + min + " " + max);
                    success = false;
                }
                if (min < 0 || max < 0) {
                    // Must be greater than 0
                    Log.e(TAG, "Min or Max Negative: " + min + " " + max);
                    success = false;
                }

                if(max > MAX_RENT) {
                    // Must be less than the max rent value
                    Log.e(TAG, "Max greater than max rent: " + min + " " + max);
                    success = false;
                }
                if(min > MAX_RENT) {
                    // Min must be less than the max rent value
                    Log.e(TAG, "Min greater than max rent: " + min + " " + max);
                    success = false;
                }

                if(success) {
                    String minString = Integer.toString(min);
                    String maxString = Integer.toString(max);

                    DaFilter.getInstance().setPriceMin(minString);
                    DaFilter.getInstance().setPriceMax(maxString);
                    Log.i(TAG, "Price filter set : " + min + " " + max);
                    refreshList(activityFragmentOrigin);
                }
                else {
                    Toast.makeText(activityFragmentOrigin,
                            "Invalid min/max price entered",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    public static void setDistanceFilter(final ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        View mView;
        final Spinner mSpinner;
        ArrayAdapter<String> adapter;
        AlertDialog dialog;

        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mView = activityFragmentOrigin.getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        mBuilder.setTitle("Set Max Distance Filter");
        mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);


        adapter = new ArrayAdapter<String>(activityFragmentOrigin, android.R.layout.simple_spinner_item,
                activityFragmentOrigin.getResources().getStringArray(R.array.distanceList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){


                    // Set DaFilter parameter here
                    String selectedItem = mSpinner.getSelectedItem().toString();
                    DaFilter.getInstance().setDistance(selectedItem);

                    Toast.makeText(activityFragmentOrigin,
                            mSpinner.getSelectedItem().toString(),
                            Toast.LENGTH_LONG)
                            .show();

                    Log.i(TAG, "Distance filter set : " + selectedItem);
                    refreshList(activityFragmentOrigin);
                }
            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    /**
     * Clear filters
     */
    public static void clearFilters(final ActivityFragmentOrigin activityFragmentOrigin) {
        Log.i(TAG, "Clearing filters!");
        Toast.makeText(activityFragmentOrigin, "Filters cleared",
                Toast.LENGTH_LONG)
                .show();
        DaFilter.getInstance().resetFilters();
        refreshList(activityFragmentOrigin);
    }

    private static void refreshList(final ActivityFragmentOrigin activityFragmentOrigin) {
        activityFragmentOrigin.createAndPopulateListingPage(ListPageFragment.ListType.MAIN_LISTING_PAGE);
    }
    /**
     * End filter button methods
     */
}
