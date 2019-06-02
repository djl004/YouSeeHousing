package com.example.youseehousing;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FilterButtonActions {
    private final static String TAG = "FilterButton";

    /**
     * Filter button methods
     * @param activityFragmentOrigin
     */
    public static void setLogOut(ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        AlertDialog dialog;
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mBuilder.setTitle("Are you sure you want to log out?");

        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                Log.i(TAG, "Log out button presesd!");
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

    public static void setSquareFtFilter(ActivityFragmentOrigin activityFragmentOrigin) {
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
                    Log.i(TAG, "Lease length filter set!");

                    // Call to DaFilter
                    String selectedItem = mSpinner.getSelectedItem().toString();
                    DaFilter.getInstance().setLeaseLength(selectedItem);

                    Toast.makeText(activityFragmentOrigin,
                            mSpinner.getSelectedItem().toString(),
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

    public static void setExtrasFilter(ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        AlertDialog dialog;
        final String[] values = {" Washer/Dryer "," Furnished "," Parking "," Pets "};
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
                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Your logic when OK button is clicked
                        Log.i(TAG, "Extras filter set!");
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

    public static void setBedsBathsFilter(ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        View mView;
        Spinner mSpinner2;
        ArrayAdapter<String> adapter2;
        Spinner mSpinner3;
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
                Log.i(TAG, "Bed/Bath filter set!");
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

    public static void setPriceFilter(ActivityFragmentOrigin activityFragmentOrigin) {
        AlertDialog.Builder mBuilder;
        View mView;
        AlertDialog dialog;
        mBuilder = new AlertDialog.Builder(activityFragmentOrigin);
        mView = activityFragmentOrigin.getLayoutInflater().inflate(R.layout.dialog_minmax, null);
        mBuilder.setTitle("Set Price (MIN/MAX) Filter");

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                ////When ok is clicked
                Log.i(TAG, "Price filter set!");
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
        mBuilder.setTitle("Set Distance Filter");
        mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);


        adapter = new ArrayAdapter<String>(activityFragmentOrigin, android.R.layout.simple_spinner_item,
                activityFragmentOrigin.getResources().getStringArray(R.array.distanceList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){
                    Log.i(TAG, "Distance filter set!");

                    // Set DaFilter parameter here
                    String selectedItem = mSpinner.getSelectedItem().toString();
                    DaFilter.getInstance().setDistance(selectedItem);

                    Toast.makeText(activityFragmentOrigin,
                            mSpinner.getSelectedItem().toString(),
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

    /**
     * Clear filters
     */
    public static void clearFilters(final ActivityFragmentOrigin activityFragmentOrigin) {
        Toast.makeText(activityFragmentOrigin, "Filters cleared",
                Toast.LENGTH_LONG)
                .show();
        DaFilter.getInstance().resetFilters();
    }

    /**
     * End filter button methods
     */
}
