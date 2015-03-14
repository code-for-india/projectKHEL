package org.cfi.projectkhel;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import org.cfi.projectkhel.data.Attendance;
import org.cfi.projectkhel.data.DataManager;
import org.cfi.projectkhel.data.DataUtils;
import org.cfi.projectkhel.data.Entry;

import static org.cfi.projectkhel.AttendanceConstants.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Attendance Activity
 *
 * TODO:
 * 1. Move all hardcoded strings into resource file.
 * 2. Generalize Dialog handling based on type (single-select, multi-select)
 * 3. Optimize DataManager calls
 */
public class AttendanceActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

  private ListView mainList;
  private Dialog ratingDialog;
  private final int SHORTEN_LEN = 15;

  private MyCustomListAdapter listAdapter;
  private List<MyCustomData> mData = new ArrayList<>();
  private int mYear;
  private int mMonthOfYear;
  private int mDayOfMonth;

  private Attendance attendance;


  private static int [] IMAGES = { R.drawable.android_calendar,
                            R.drawable.android_earth,
                            R.drawable.android_friends,
                            R.drawable.android_add_contact,
                            R.drawable.aperture,
                            R.drawable.happy,
                            R.drawable.clipboard};
  // TODO move to resources instead.
  private static String [] LABELS = { "Date", "Location", "Coordinators", "Beneficiary", "Modules", "Rating", "Comments"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_attendance);

    mainList = (ListView) findViewById(R.id.listView1);
    mainList.setOnItemClickListener(this);

    initialize();

    // Set our custom adapter for the listView.
    listAdapter = new MyCustomListAdapter();
    mainList.setAdapter(listAdapter);
    listAdapter.refresh();
  }

  private void initialize() {
    // TODO: Fetch the logged in user.
    attendance = new Attendance("TODO");

    populateListContents();

    final Calendar c = Calendar.getInstance();
    mYear = c.get(Calendar.YEAR);
    mMonthOfYear = c.get(Calendar.MONTH);
    mDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_attendance, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void onSubmitClick(View v) {
    Log.d(TAG, "Attendance: " +  attendance);
    // TODO - Verify if all data is filled up, Ask for confirmation first.
    new AlertDialog.Builder(this)
        .setTitle("Submit Attendance?")
        .setMessage("Did you fill up everything for this Event?")
        .setIcon(android.R.drawable.ic_menu_compass)
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

          public void onClick(DialogInterface dialog, int whichButton) {
            Toast.makeText(AttendanceActivity.this, "Attendance data submitted", Toast.LENGTH_SHORT).show();
            finish();
          }})
        .setNegativeButton(android.R.string.no, null).show();

  }

  public void onResetClick(View v) {

  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Log.d(TAG, "Clicked on position: " + position + " id = " + id);
    final MyCustomData dataRow = mData.get(position);
    switch(position) {
      case ROW_DATE:
        handleDateDialog(dataRow);
        break;
      case ROW_RATING:
        handleRatingDialog(dataRow);
        break;
      case ROW_LOCATION:
        handleLocationDialog(dataRow);
        break;
      case ROW_BENEFICIARIES:
        handleBeneficiariesDialog(dataRow);
        break;
      case ROW_MODULES:
        handleModulesDialog(dataRow);
        break;
      case ROW_COORDINATORS:
        handleCoordinatorsDialog(dataRow);
        break;
      case ROW_COMMENTS:
        handleCommentsDialog(dataRow);
        break;
      default:
        break;
    }
    listAdapter.refresh();
  }


  private void handleDateDialog(final MyCustomData dataRow) {
    final DatePickerDialog dpd = new DatePickerDialog(this,
        new DatePickerDialog.OnDateSetListener() {

          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dataRow.setContent(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            mYear = year;
            mMonthOfYear = monthOfYear;
            mDayOfMonth = dayOfMonth;
            attendance.setDate(year, monthOfYear, dayOfMonth);
          }
        }, mYear, mMonthOfYear, mDayOfMonth);
    dpd.show();
  }

  private void handleRatingDialog(final MyCustomData dataRow) {
    if (ratingDialog == null) {
      ratingDialog = new Dialog(AttendanceActivity.this, R.style.NoTitleDialog);
      ratingDialog.setContentView(R.layout.rating_dialog);
      ratingDialog.setCancelable(true);
    }

    final RatingBar ratingBar = (RatingBar)ratingDialog.findViewById(R.id.dialog_ratingbar);
    ratingBar.setRating(attendance.getRating() * 1.0f);

    final Button updateButton = (Button) ratingDialog.findViewById(R.id.rank_dialog_button);
    updateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final int userRankValue = (int) ratingBar.getRating();
        attendance.setRating(userRankValue);
        dataRow.setContent(Integer.toString(userRankValue));
        ratingDialog.dismiss();
      }
    });
    //now that the dialog is set up, it's time to show it
    ratingDialog.show();
  }

  private void handleCommentsDialog(final MyCustomData dataRow) {
    // TODO - Move all hardcoded strings to resource files.
    final EditText commentsTxt = new EditText(this);
    commentsTxt.setHint("How did the session go?");
    commentsTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

    new AlertDialog.Builder(this)
        .setTitle("Enter Comments")
        .setView(commentsTxt)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            final String comment = commentsTxt.getText().toString();
            dataRow.setContent(shortenIt(comment));
            attendance.setComments(comment);
          }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
          }
        })
        .show();
  }

  private void handleCoordinatorsDialog(final MyCustomData dataRow) {
    final CharSequence coordinatorNames[] = DataUtils.getEntryNames(DataManager.getInstance().getCoordinators());
    boolean[] checkItems = DataUtils.getSelectedItemsFromIds(attendance.getCoordinators(),
                                                    DataManager.getInstance().getCoordinators());
    // List where we track the selected items
    final List<Integer> selectedItems = DataUtils.getSelectedItemsFromBoolList(checkItems);

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // Set the dialog title
    builder.setTitle("Pick Coordinators")
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        .setMultiChoiceItems(coordinatorNames, checkItems,
            new DialogInterface.OnMultiChoiceClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                  // If the user checked the item, add it to the selected items
                  selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                  // Else, if the item is already in the array, remove it
                  selectedItems.remove(Integer.valueOf(which));
                }
              }
            })
            // Set the action buttons
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // User clicked OK, so save the mSelectedItems results somewhere
            // or return them to the component that opened the dialog
            dataRow.setContent(" [ " + selectedItems.size() + " ] ");
            attendance.addCoordinators(DataUtils.getIdsFromSelectedItems(selectedItems,
                DataManager.getInstance().getCoordinators()));
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // do nothing
          }
        });

    builder.show();
  }

  private void handleModulesDialog(final MyCustomData dataRow) {
    final boolean[] checkItems = DataUtils.getSelectedItemsFromIds(attendance.getModules(),
                                                             DataManager.getInstance().getModules());
    // List where we track the selected items
    final List<Integer> selectedItems = DataUtils.getSelectedItemsFromBoolList(checkItems);

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // Set the dialog title
    builder.setTitle("Pick Modules")
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        .setMultiChoiceItems(DataUtils.getEntryNames(DataManager.getInstance().getModules()), checkItems,
            new DialogInterface.OnMultiChoiceClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                  // If the user checked the item, add it to the selected items
                  selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                  // Else, if the item is already in the array, remove it
                  selectedItems.remove(Integer.valueOf(which));
                }
              }
            })
            // Set the action buttons
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // User clicked OK, so save the mSelectedItems results somewhere
            // or return them to the component that opened the dialog
            dataRow.setContent(" [ " + selectedItems.size() + " ] ");
            attendance.addModules(DataUtils.getIdsFromSelectedItems(selectedItems,
                                  DataManager.getInstance().getModules()));
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // do nothing
          }
        });

    builder.show();
  }

  private void handleBeneficiariesDialog(final MyCustomData dataRow) {
    if (attendance.getLocation() <= 0) {
      Toast.makeText(this, "Please select a location first", Toast.LENGTH_SHORT).show();
      return;
    }
    final List<Entry> beneficiaries = DataManager.getInstance().getBeneficiariesForLocation(attendance.getLocation());
    final CharSequence beneficiaryNames[] = DataUtils.getEntryNames(beneficiaries);
    boolean[] checkItems = DataUtils.getSelectedItemsFromIds(attendance.getBeneficiaries(), beneficiaries);

    // List where we track the selected items
    final List<Integer> selectedItems = DataUtils.getSelectedItemsFromBoolList(checkItems);

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // Set the dialog title
    builder.setTitle("Pick Beneficiaries")
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        .setMultiChoiceItems(beneficiaryNames, checkItems,
            new DialogInterface.OnMultiChoiceClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                  // If the user checked the item, add it to the selected items
                  selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                  // Else, if the item is already in the array, remove it
                  selectedItems.remove(Integer.valueOf(which));
                }
              }
            })
            // Set the action buttons
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // User clicked OK, so save the mSelectedItems results somewhere
            // or return them to the component that opened the dialog
            dataRow.setContent(" [ " + selectedItems.size() + " ] ");
            attendance.addBeneficiaries(DataUtils.getIdsFromSelectedItems(selectedItems, beneficiaries));
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // do nothing
          }
        });

    builder.show();
  }

  private void handleLocationDialog(final MyCustomData dataRow) {
    final int checkItem = DataUtils.getSelectedItemFromId(attendance.getLocation(),
                                                    DataManager.getInstance().getLocations());
    // List where we track the selected item
    final Integer selectedItem[] = new Integer[1];
    selectedItem[0] = checkItem;
    final CharSequence[] locItems = DataUtils.getEntryNames(DataManager.getInstance().getLocations());

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // Set the dialog title
    builder.setTitle("Pick Location")
        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        . setSingleChoiceItems(locItems, checkItem,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                selectedItem[0] = which;
              }
            })
            // Set the action buttons
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // User clicked OK, so save the mSelectedItems results somewhere
            // or return them to the component that opened the dialog
            dataRow.setContent(locItems[selectedItem[0]].toString());
            attendance.setLocation(DataUtils.getIdFromSelectedItem(selectedItem[0],
                DataManager.getInstance().getLocations()));
          }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // do nothing
          }
        });

    builder.show();
  }

  /**
   * Implement a Custom Adapter for rendering the different layout for each of the Various rows
   * in this mainList.
   * Row 0 - Date
   * Row 1 - Location - SingleSelect
   * Row 2 - Coordinators - MultiSelect
   * Row 3 - Beneficiaries - MultiSelect
   * Row 4 - Modules - MultiSelect
   * Row 5 - Overall Rating - Dropdown
   * Row 6 - Comments - EditText
   */
  private class MyCustomListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    public MyCustomListAdapter() {
      mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
      return mData.size();
    }

    @Override
    public Object getItem(int position) {
      return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    public void refresh() {
      notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      MyViewHolder viewHolder = null;
      if (convertView == null) {
        // No convertView in recycle pool yet
        convertView = mInflater.inflate(R.layout.attendance_row, parent, false);
        viewHolder = new MyViewHolder(convertView);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (MyViewHolder) convertView.getTag();
        //Log.d(TAG, "Reusing Row for " + position);
      }
      viewHolder.setValues(mData.get(position));
      return convertView;
    }

    class MyViewHolder {
      ImageView myImg;
      TextView myLabel;
      TextView  myContent;

      MyViewHolder(View v) {
        myImg =  (ImageView) v.findViewById(R.id.attIconImageView);
        myLabel = (TextView) v.findViewById(R.id.attLabelTextView);
        myContent = (TextView) v.findViewById(R.id.attContentTextView);
      }

      void setValues(MyCustomData customData) {
        myImg.setImageResource(customData.imageResource);
        myLabel.setText(customData.label);
        myContent.setText(customData.content);
      }
    }
  }

  private void populateListContents() {
    for (int i = 0; i < LABELS.length; i++) {
      mData.add(new MyCustomData(IMAGES[i], LABELS[i], ""));
    }
  }

  /**
   * Custom Data class to store the individual row values.
   */
  private class MyCustomData {
    int imageResource;
    String label;
    String content;

    public MyCustomData(int imageResource, String label, String content) {
      this.imageResource = imageResource;
      this.label = label;
      this.content = content;
    }

    public void setContent(String myContent) {
      content = myContent;
    }

  }

  private String shortenIt(String data) {
    return data.substring(0, data.length() > SHORTEN_LEN ? SHORTEN_LEN : data.length()) + "...";
  }

}
