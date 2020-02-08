package com.Example.stylishnamemaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.internal.view.SupportMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Example.stylishnamemaker.constant.Constant;
import com.Example.stylishnamemaker.constant.ImageScannerAdapter;
import com.Example.stylishnamemaker.constant.SaveToStorageUtil;
import com.Example.stylishnamemaker.data.DataClass;
import com.Example.stylishnamemaker.dragListener.DraggableBitmap;
import com.Example.stylishnamemaker.dragListener.DraggableImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ActivitySolutioning extends Activity implements OnClickListener, OnTouchListener {
    private static int screenHeight;
    private static int screenWidth;
    private ArrayList<Integer> arrayList = new ArrayList();
    private RelativeLayout bottomView;
    private int colorCode = 0;
    private DraggableImageView editPhoto;
    public Bitmap final_bitmap;
    private String firstLine = null;
    private String[] font = new String[]{"f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20", "f21", "f22", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30", "f31", "f32", "f33", "f34", "f35", "f36", "f37", "f38"};
    private HashMap<Integer, Bitmap> fontCache = new HashMap();
    private ImageView fontColorBtn;
    private int[] fontImg = new int[]{R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5, R.drawable.f6, R.drawable.f7, R.drawable.f8, R.drawable.f9, R.drawable.f10, R.drawable.f11, R.drawable.f12, R.drawable.f13, R.drawable.f14, R.drawable.f15, R.drawable.f16, R.drawable.f17, R.drawable.f18, R.drawable.f19, R.drawable.f20, R.drawable.f21, R.drawable.f22, R.drawable.f23, R.drawable.f24, R.drawable.f25, R.drawable.f26, R.drawable.f27, R.drawable.f28, R.drawable.f29, R.drawable.f30, R.drawable.f31, R.drawable.f32, R.drawable.f33, R.drawable.f34, R.drawable.f35, R.drawable.f36, R.drawable.f37, R.drawable.f38};
    private int fontIndex = 2;
    private ImageView fontStyleBtn;
    private Bitmap frameBgBitmap = null;
    private ImageView frameBtn;
    private ArrayList<Bitmap> framesBitmap = new ArrayList();
    private HashMap<String, Integer> hashMapCounter = new HashMap();
    private int hashmapCounter = -1;

    private EditText nameEditText;
    private ImageView nextBtn;
    private ImageView okTxtBtn;
    private ArrayList<Integer> redoArrayList = new ArrayList();
    private ImageView redoBtn;
    private ArrayList<Bitmap> redoFramesBitmap = new ArrayList();
    private ArrayList<DataClass> redoTxt1ArrayList = new ArrayList();
    private ArrayList<DataClass> redoTxt2ArrayList = new ArrayList();
    private ArrayList<DataClass> redoTxtArrayList = new ArrayList();
    private ImageView resetBtn;
    private String savedImagePath;
    private String secondLine = null;
    private SharedPreferences sharedPreferences;
    private String thirldLine = null;
    private ArrayList<DataClass> txt1ArrayList = new ArrayList();
    private ArrayList<DataClass> txt2ArrayList = new ArrayList();
    private ArrayList<DataClass> txtArrayList = new ArrayList();
    private ImageView txtBtn;
    private TextView txtLength;
    private ImageView undoBtn;

    public class FontAdapter extends BaseAdapter {
        final /* synthetic */ boolean $assertionsDisabled = (!ActivitySolutioning.class.desiredAssertionStatus());
        private LayoutInflater inflater;

        FontAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return ActivitySolutioning.this.fontImg.length;
        }

        public Object getItem(int position) {
            return Integer.valueOf(ActivitySolutioning.this.fontImg[position]);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = this.inflater.inflate(R.layout.text_font_adapter, parent, false);
                holder = new ViewHolder();
                if ($assertionsDisabled || view != null) {
                    holder.imageView = (ImageView) view.findViewById(R.id.image);
                    view.setTag(holder);
                } else {
                    throw new AssertionError();
                }
            }
            holder = (ViewHolder) view.getTag();
            holder.imageView.setImageBitmap(ActivitySolutioning.this.cacheImageFont(Integer.valueOf(position)));
            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;

        ViewHolder() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.tatto_act);

        this.sharedPreferences = getSharedPreferences(Constant.SETTING_PREFERENCE, 0);
        this.editPhoto = (DraggableImageView) findViewById(R.id.editPhoto);
        this.bottomView = (RelativeLayout) findViewById(R.id.bottomView);
        this.nameEditText = (EditText) findViewById(R.id.nameEditText);
        this.txtLength = (TextView) findViewById(R.id.txtLength);
        this.okTxtBtn = (ImageView) findViewById(R.id.okTxtBtn1);
        this.frameBtn = (ImageView) findViewById(R.id.frameBtn);
        this.txtBtn = (ImageView) findViewById(R.id.txtBtn);
        this.fontStyleBtn = (ImageView) findViewById(R.id.fontStyleBtn);
        this.fontColorBtn = (ImageView) findViewById(R.id.fontColorBtn);
        this.undoBtn = (ImageView) findViewById(R.id.undoBtn);
        this.redoBtn = (ImageView) findViewById(R.id.redoBtn);
        this.resetBtn = (ImageView) findViewById(R.id.resetBtn);
        this.nextBtn = (ImageView) findViewById(R.id.nextBtn);
        this.colorCode = SupportMenu.CATEGORY_MASK;
        DisplayMetrics displayMatrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMatrics);
        screenWidth = displayMatrics.widthPixels;
        screenHeight = displayMatrics.heightPixels;
        try {
            initialValue();
            if (this.frameBgBitmap == null) {
                this.frameBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.frameBgBitmap = Constant.createScaledBitmap(this.frameBgBitmap, ScalingUtilities.ScalingLogic.FIT, screenWidth, screenHeight);
        this.editPhoto.setImageBitmap(this.frameBgBitmap);
        this.okTxtBtn.setOnClickListener(this);
        this.frameBtn.setOnClickListener(this);
        this.txtBtn.setOnClickListener(this);
        this.fontStyleBtn.setOnClickListener(this);
        this.fontColorBtn.setOnClickListener(this);
        this.undoBtn.setOnClickListener(this);
        this.redoBtn.setOnClickListener(this);
        this.resetBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);
        this.nameEditText.setOnTouchListener(this);
        this.bottomView.bringToFront();
        this.nameEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ActivitySolutioning.this.txtLength.setText("30/" + s.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    private Bitmap initialValue() throws IOException {
        this.frameBgBitmap = BitmapFactory.decodeResource(getResources(), Constant.imageGallery[new Random().nextInt(21) + 0]);
        return this.frameBgBitmap;
    }


    private void undoCalling() {
        DraggableBitmap stamp;
        int index = ((Integer) this.arrayList.get(this.arrayList.size() - 1)).intValue();
        if (this.hashMapCounter.containsKey("TxtLogo0") && index == ((Integer) this.hashMapCounter.get("TxtLogo0")).intValue() && this.txtArrayList.size() > 0) {
            this.editPhoto.undoBitmap(index);
            this.redoTxtArrayList.add(this.txtArrayList.get(this.txtArrayList.size() - 1));
            this.txtArrayList.remove(this.txtArrayList.size() - 1);
            this.redoArrayList.add(this.arrayList.get(this.arrayList.size() - 1));
            this.arrayList.remove(this.arrayList.size() - 1);
            if (this.txtArrayList.size() > 0) {
                stamp = new DraggableBitmap(((DataClass) this.txtArrayList.get(this.txtArrayList.size() - 1)).hashBitmap);
                if (this.hashMapCounter.containsKey("TxtLogo0")) {
                    this.editPhoto.replaceOverlayBitmap(stamp, index);
                } else {
                    this.editPhoto.addOverlayBitmap(stamp);
                }
            }
        }
        if (this.hashMapCounter.containsKey("TxtLogo1") && index == ((Integer) this.hashMapCounter.get("TxtLogo1")).intValue() && this.txt1ArrayList.size() > 0) {
            this.editPhoto.undoBitmap(index);
            this.redoTxt1ArrayList.add(this.txt1ArrayList.get(this.txt1ArrayList.size() - 1));
            this.txt1ArrayList.remove(this.txt1ArrayList.size() - 1);
            this.redoArrayList.add(this.arrayList.get(this.arrayList.size() - 1));
            this.arrayList.remove(this.arrayList.size() - 1);
            if (this.txt1ArrayList.size() > 0) {
                stamp = new DraggableBitmap(((DataClass) this.txt1ArrayList.get(this.txt1ArrayList.size() - 1)).hashBitmap);
                if (this.hashMapCounter.containsKey("TxtLogo1")) {
                    this.editPhoto.replaceOverlayBitmap(stamp, index);
                } else {
                    this.editPhoto.addOverlayBitmap(stamp);
                }
            }
        }
        if (this.hashMapCounter.containsKey("TxtLogo2") && index == ((Integer) this.hashMapCounter.get("TxtLogo2")).intValue() && this.txt2ArrayList.size() > 0) {
            this.editPhoto.undoBitmap(index);
            this.redoTxt2ArrayList.add(this.txt2ArrayList.get(this.txt2ArrayList.size() - 1));
            this.txt2ArrayList.remove(this.txt2ArrayList.size() - 1);
            this.redoArrayList.add(this.arrayList.get(this.arrayList.size() - 1));
            this.arrayList.remove(this.arrayList.size() - 1);
            if (this.txt2ArrayList.size() > 0) {
                stamp = new DraggableBitmap(((DataClass) this.txt2ArrayList.get(this.txt2ArrayList.size() - 1)).hashBitmap);
                if (this.hashMapCounter.containsKey("TxtLogo2")) {
                    this.editPhoto.replaceOverlayBitmap(stamp, index);
                } else {
                    this.editPhoto.addOverlayBitmap(stamp);
                }
            }
        }
    }

    private void redoCalling() {
        DraggableBitmap stamp;
        int index = ((Integer) this.redoArrayList.get(this.redoArrayList.size() - 1)).intValue();
        if (this.hashMapCounter.containsKey("BackBG") && index == ((Integer) this.hashMapCounter.get("BackBG")).intValue() && this.redoFramesBitmap.size() > 0) {
            this.editPhoto.setImageBitmap(null);
            this.editPhoto.destroyDrawingCache();
            Bitmap tempBackBitmap = (Bitmap) this.redoFramesBitmap.get(this.redoFramesBitmap.size() - 1);
            if (tempBackBitmap != null) {
                this.editPhoto.setImageBitmap(Constant.createScaledBitmap(tempBackBitmap, ScalingUtilities.ScalingLogic.FIT, screenWidth, screenHeight));
                this.framesBitmap.add(this.redoFramesBitmap.get(this.redoFramesBitmap.size() - 1));
                this.redoFramesBitmap.remove(this.redoFramesBitmap.size() - 1);
                this.arrayList.add(this.redoArrayList.get(this.redoArrayList.size() - 1));
                this.redoArrayList.remove(this.redoArrayList.size() - 1);
            }
        }
        if (this.hashMapCounter.containsKey("TxtLogo0") && index == ((Integer) this.hashMapCounter.get("TxtLogo0")).intValue() && this.redoTxtArrayList.size() > 0) {
            stamp = new DraggableBitmap(((DataClass) this.redoTxtArrayList.get(this.redoTxtArrayList.size() - 1)).hashBitmap);
            if (this.hashMapCounter.containsKey("TxtLogo0")) {
                this.editPhoto.replaceOverlayBitmap(stamp, index);
            } else {
                this.editPhoto.addOverlayBitmap(stamp);
            }
            this.txtArrayList.add(this.redoTxtArrayList.get(this.redoTxtArrayList.size() - 1));
            this.redoTxtArrayList.remove(this.redoTxtArrayList.size() - 1);
            this.arrayList.add(this.redoArrayList.get(this.redoArrayList.size() - 1));
            this.redoArrayList.remove(this.redoArrayList.size() - 1);
        }
        if (this.hashMapCounter.containsKey("TxtLogo1") && index == ((Integer) this.hashMapCounter.get("TxtLogo1")).intValue() && this.redoTxt1ArrayList.size() > 0) {
            stamp = new DraggableBitmap(((DataClass) this.redoTxt1ArrayList.get(this.redoTxt1ArrayList.size() - 1)).hashBitmap);
            if (this.hashMapCounter.containsKey("TxtLogo1")) {
                this.editPhoto.replaceOverlayBitmap(stamp, index);
            } else {
                this.editPhoto.addOverlayBitmap(stamp);
            }
            this.txt1ArrayList.add(this.redoTxt1ArrayList.get(this.redoTxt1ArrayList.size() - 1));
            this.redoTxt1ArrayList.remove(this.redoTxt1ArrayList.size() - 1);
            this.arrayList.add(this.redoArrayList.get(this.redoArrayList.size() - 1));
            this.redoArrayList.remove(this.redoArrayList.size() - 1);
        }
        if (this.hashMapCounter.containsKey("TxtLogo2") && index == ((Integer) this.hashMapCounter.get("TxtLogo2")).intValue() && this.redoTxt2ArrayList.size() > 0) {
            stamp = new DraggableBitmap(((DataClass) this.redoTxt2ArrayList.get(this.redoTxt2ArrayList.size() - 1)).hashBitmap);
            if (this.hashMapCounter.containsKey("TxtLogo2")) {
                this.editPhoto.replaceOverlayBitmap(stamp, index);
            } else {
                this.editPhoto.addOverlayBitmap(stamp);
            }
            this.txt2ArrayList.add(this.redoTxt2ArrayList.get(this.redoTxt2ArrayList.size() - 1));
            this.redoTxt2ArrayList.remove(this.redoTxt2ArrayList.size() - 1);
            this.arrayList.add(this.redoArrayList.get(this.redoArrayList.size() - 1));
            this.redoArrayList.remove(this.redoArrayList.size() - 1);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.undoBtn:
                if (this.arrayList.size() > 0) {
                    undoCalling();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "No step for Undo", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.redoBtn:
                if (this.redoArrayList.size() > 0) {
                    redoCalling();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "No step for Redo", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.okTxtBtn1:
                if (this.nameEditText.getText().length() <= 0) {
                    Toast.makeText(this, "No Txt Enter..", Toast.LENGTH_LONG).show();
                    return;
                } else if (this.nameEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "No Txt Enter..", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int peotryLine = 0;
                    if (this.txtArrayList.size() <= 0 || this.txt1ArrayList.size() <= 0 || this.txt2ArrayList.size() <= 0) {
                        if (this.txtArrayList.size() == 0) {
                            this.firstLine = this.nameEditText.getText().toString();
                            peotryLine = 0;
                        } else if (this.txt1ArrayList.size() == 0) {
                            this.secondLine = this.nameEditText.getText().toString();
                            peotryLine = 1;
                        } else if (this.txt2ArrayList.size() == 0) {
                            this.thirldLine = this.nameEditText.getText().toString();
                            peotryLine = 2;
                        }
                        Bitmap bitmap = textAsBitmap(this.nameEditText.getText().toString(), 80.0f, this.colorCode);
                        if (bitmap != null) {
                            setTatooImage(bitmap, "TxtLogo" + peotryLine);
                            this.nameEditText.setText("");
                            return;
                        }
                        Toast.makeText(this, "Error..", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(this, "you can write only three lines Name..", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.frameBtn:
                startActivityForResult(new Intent(this, AcitivityCategoryBG.class), 4);
                return;
            case R.id.txtBtn:
                boolean textFond = false;
                if (this.txtArrayList.size() > 0 || this.txt1ArrayList.size() > 0 || this.txt2ArrayList.size() > 0) {
                    textFond = true;
                }
                if (textFond) {
                    editTextPopup();
                    return;
                } else {
                    Toast.makeText(this, "No Txt for edit..", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.fontColorBtn:
                showColorPickerDialogDemo();
                return;
            case R.id.fontStyleBtn:
                if (this.txtArrayList.size() > 0) {
                    fontPopup();
                    return;
                } else {
                    Toast.makeText(this, "No Txt Enter..", Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.resetBtn:
                if (this.arrayList.size() > 0) {
                    removetatooImage();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.noitem), Toast.LENGTH_LONG).show();
                    return;
                }
            case R.id.nextBtn:

                saveTemporaryBitmapImage();
                return;

            default:
                return;
        }
    }

    private void fontPopup() {
        final Dialog dialog = new Dialog(this, R.style.CustomStyle);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.font_popup);
        dialog.setCancelable(true);
        TextView popupHeaderTxtView = (TextView) dialog.findViewById(R.id.popupHeaderTxtView);
        popupHeaderTxtView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/f4.ttf"));
        popupHeaderTxtView.setText("Select Text Font");
        GridView gridView = (GridView) dialog.findViewById(R.id.gridView);
        gridView.setAdapter(new FontAdapter(this));
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ActivitySolutioning.this.fontIndex = position;
                ActivitySolutioning.this.firstLine = null;
                ActivitySolutioning.this.secondLine = null;
                ActivitySolutioning.this.thirldLine = null;
                ActivitySolutioning.this.firstLine = ((DataClass) ActivitySolutioning.this.txtArrayList.get(ActivitySolutioning.this.txtArrayList.size() - 1)).arrayListEditText;
                if (ActivitySolutioning.this.txt1ArrayList.size() > 0) {
                    ActivitySolutioning.this.secondLine = ((DataClass) ActivitySolutioning.this.txt1ArrayList.get(ActivitySolutioning.this.txt1ArrayList.size() - 1)).arrayListEditText;
                }
                if (ActivitySolutioning.this.txt2ArrayList.size() > 0) {
                    ActivitySolutioning.this.thirldLine = ((DataClass) ActivitySolutioning.this.txt2ArrayList.get(ActivitySolutioning.this.txt2ArrayList.size() - 1)).arrayListEditText;
                }
                if (ActivitySolutioning.this.firstLine != null) {
                    ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.firstLine, 80.0f, ActivitySolutioning.this.colorCode), "TxtLogo0");
                }
                if (ActivitySolutioning.this.secondLine != null) {
                    ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.secondLine, 80.0f, ActivitySolutioning.this.colorCode), "TxtLogo1");
                }
                if (ActivitySolutioning.this.thirldLine != null) {
                    ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.thirldLine, 80.0f, ActivitySolutioning.this.colorCode), "TxtLogo2");
                }
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private Bitmap cacheImageFont(Integer position) {
        Bitmap captureBitmap = null;
        try {
            if (this.fontCache.containsKey(position)) {
                return (Bitmap) this.fontCache.get(position);
            }
            Options options = new Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Config.RGB_565;
            options.inSampleSize = 1;
            captureBitmap = BitmapFactory.decodeResource(getResources(), this.fontImg[position.intValue()], options);
            captureBitmap = Bitmap.createScaledBitmap(captureBitmap, captureBitmap.getWidth(), captureBitmap.getHeight(), true);
            this.fontCache.put(position, captureBitmap);
            return captureBitmap;
        } catch (OutOfMemoryError e) {
            this.fontCache.clear();
        }
        return captureBitmap;
    }

    private void editTextPopup() {
        final Dialog dialog = new Dialog(this, R.style.CustomStyle);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.popup_edittext);
        dialog.setCancelable(true);
        final EditText popUpEditTxt1 = (EditText) dialog.findViewById(R.id.popUpEditTxt1);
        final EditText popUpEditTxt2 = (EditText) dialog.findViewById(R.id.popUpEditTxt2);
        final EditText popUpEditTxt3 = (EditText) dialog.findViewById(R.id.popUpEditTxt3);
        ImageView popupOkButton = (ImageView) dialog.findViewById(R.id.popupOkButton);
        ImageView popupCancelButton = (ImageView) dialog.findViewById(R.id.popupCancelButton);
        if (this.txtArrayList.size() > 0) {
            popUpEditTxt1.setText(((DataClass) this.txtArrayList.get(this.txtArrayList.size() - 1)).arrayListEditText);
        }
        if (this.txt1ArrayList.size() > 0) {
            popUpEditTxt2.setVisibility(View.VISIBLE);
            popUpEditTxt2.setText(((DataClass) this.txt1ArrayList.get(this.txt1ArrayList.size() - 1)).arrayListEditText);
        } else {
            popUpEditTxt2.setVisibility(View.GONE);
        }
        if (this.txt2ArrayList.size() > 0) {
            popUpEditTxt3.setVisibility(View.VISIBLE);
            popUpEditTxt3.setText(((DataClass) this.txt2ArrayList.get(this.txt2ArrayList.size() - 1)).arrayListEditText);
        } else {
            popUpEditTxt3.setVisibility(View.GONE);
        }
        popupOkButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                boolean Line1 = true;
                boolean Line2 = true;
                boolean Line3 = true;
                ActivitySolutioning.this.firstLine = null;
                ActivitySolutioning.this.secondLine = null;
                ActivitySolutioning.this.thirldLine = null;
                if (popUpEditTxt1.getVisibility() == View.VISIBLE) {
                    if (popUpEditTxt1.getText() == null) {
                        Line1 = false;
                        Toast.makeText(ActivitySolutioning.this, "No Txt Enter First line..", Toast.LENGTH_LONG).show();
                    } else if (popUpEditTxt1.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(ActivitySolutioning.this, "No Txt Enter First line..", Toast.LENGTH_LONG).show();
                        popUpEditTxt1.setText("");
                        Line1 = false;
                    } else {
                        ActivitySolutioning.this.firstLine = popUpEditTxt1.getText().toString();
                    }
                }
                if (popUpEditTxt2.getVisibility() == View.VISIBLE) {
                    if (popUpEditTxt2.getText() == null) {
                        Line2 = false;
                        Toast.makeText(ActivitySolutioning.this, "No Txt Enter Second line..", Toast.LENGTH_LONG).show();
                    } else if (popUpEditTxt2.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(ActivitySolutioning.this, "No Txt Enter Second line..", Toast.LENGTH_LONG).show();
                        popUpEditTxt2.setText("");
                        Line2 = false;
                    } else {
                        ActivitySolutioning.this.secondLine = popUpEditTxt2.getText().toString();
                    }
                }
                if (popUpEditTxt3.getVisibility() == View.VISIBLE) {
                    if (popUpEditTxt3.getText() == null) {
                        Line3 = false;
                        Toast.makeText(ActivitySolutioning.this, "No Txt Enter Thirld line..", Toast.LENGTH_LONG).show();
                    } else if (popUpEditTxt3.getText().toString().equalsIgnoreCase("")) {
                        Toast.makeText(ActivitySolutioning.this, "No Txt Enter Thirld line..", Toast.LENGTH_LONG).show();
                        popUpEditTxt3.setText("");
                        Line3 = false;
                    } else {
                        ActivitySolutioning.this.thirldLine = popUpEditTxt3.getText().toString();
                    }
                }
                if (Line1 && Line2 && Line3) {
                    if (ActivitySolutioning.this.firstLine != null) {
                        ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.firstLine, 80.0f, ActivitySolutioning.this.colorCode), "TxtLogo0");
                    }
                    if (ActivitySolutioning.this.secondLine != null) {
                        ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.secondLine, 80.0f, ActivitySolutioning.this.colorCode), "TxtLogo1");
                    }
                    if (ActivitySolutioning.this.thirldLine != null) {
                        ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.thirldLine, 80.0f, ActivitySolutioning.this.colorCode), "TxtLogo2");
                    }
                    popUpEditTxt1.setText("");
                    popUpEditTxt2.setText("");
                    popUpEditTxt3.setText("");
                    dialog.cancel();
                }
            }
        });
        popupCancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                popUpEditTxt1.setText("");
                popUpEditTxt2.setText("");
                popUpEditTxt3.setText("");
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void saveTemporaryBitmapImage() {
        this.editPhoto.setDrawingCacheEnabled(true);
        this.final_bitmap = Bitmap.createBitmap(this.editPhoto.getDrawingCache());
        this.editPhoto.setDrawingCacheEnabled(false);
        this.savedImagePath = SaveToStorageUtil.save(this.final_bitmap, this);
        new ImageScannerAdapter(this).scanImage(this.savedImagePath);
        // String uri = Uri.parse("file:///" + this.savedImagePath).toString();
        Intent intent = new Intent(this, ActivitySavingSolution.class);
        intent.putExtra("ImgUrl", savedImagePath);
        startActivity(intent);
//        Google_Itrestial_Ads(ActivitySolutioning.this);
    }

    private void removetatooImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.removeTitle));
        builder.setMessage(getResources().getString(R.string.removetxt));
        builder.setPositiveButton(getResources().getString(R.string.yestxt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivitySolutioning.this.editPhoto.removeAll();
                ActivitySolutioning.this.frameBgBitmap = Constant.createScaledBitmap(ActivitySolutioning.this.frameBgBitmap, ScalingUtilities.ScalingLogic.FIT, ActivitySolutioning.screenWidth, ActivitySolutioning.screenHeight);
                ActivitySolutioning.this.editPhoto.setImageBitmap(ActivitySolutioning.this.frameBgBitmap);
                ActivitySolutioning.this.hashmapCounter = -1;
                ActivitySolutioning.this.hashMapCounter = new HashMap();
                ActivitySolutioning.this.arrayList.clear();
                ActivitySolutioning.this.redoArrayList.clear();
                ActivitySolutioning.this.framesBitmap.clear();
                ActivitySolutioning.this.txtArrayList.clear();
                ActivitySolutioning.this.txt1ArrayList.clear();
                ActivitySolutioning.this.txt2ArrayList.clear();
                ActivitySolutioning.this.redoFramesBitmap.clear();
                ActivitySolutioning.this.redoTxtArrayList.clear();
                ActivitySolutioning.this.redoTxt1ArrayList.clear();
                ActivitySolutioning.this.redoTxt2ArrayList.clear();
                ActivitySolutioning.this.colorCode = SupportMenu.CATEGORY_MASK;
                ActivitySolutioning.this.fontIndex = 2;
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.canceltxt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
    }

    private void setTatooImage(Bitmap bmp, String string) {
        DraggableBitmap stamp = new DraggableBitmap(bmp);
        int index;
        if (string.equalsIgnoreCase("BackBG")) {
            index = -1;
            if (this.hashMapCounter.containsKey("BackBG")) {
                index = ((Integer) this.hashMapCounter.get("BackBG")).intValue();
            } else {
                this.hashMapCounter.put("BackBG", Integer.valueOf(-1));
            }
            this.editPhoto.setImageBitmap(null);
            this.editPhoto.destroyDrawingCache();
            this.editPhoto.setImageBitmap(bmp);
            this.arrayList.add(Integer.valueOf(index));
        } else if (string.equalsIgnoreCase("TxtLogo0")) {
            if (this.hashMapCounter.containsKey("TxtLogo0")) {
                index = ((Integer) this.hashMapCounter.get("TxtLogo0")).intValue();
                this.editPhoto.replaceOverlayBitmap(stamp, index);
            } else {
                index = this.hashmapCounter + 1;
                this.hashmapCounter = index;
                this.hashMapCounter.put("TxtLogo0", Integer.valueOf(this.hashmapCounter));
                this.editPhoto.addOverlayBitmap(stamp);
            }
            this.arrayList.add(Integer.valueOf(index));
            this.txtArrayList.add(new DataClass(bmp, this.firstLine));
        } else if (string.equalsIgnoreCase("TxtLogo1")) {
            if (this.hashMapCounter.containsKey("TxtLogo1")) {
                index = ((Integer) this.hashMapCounter.get("TxtLogo1")).intValue();
                this.editPhoto.replaceOverlayBitmap(stamp, index);
            } else {
                index = this.hashmapCounter + 1;
                this.hashmapCounter = index;
                this.hashMapCounter.put("TxtLogo1", Integer.valueOf(this.hashmapCounter));
                this.editPhoto.addOverlayBitmap(stamp);
            }
            this.arrayList.add(Integer.valueOf(index));
            this.txt1ArrayList.add(new DataClass(bmp, this.secondLine));
        } else if (string.equalsIgnoreCase("TxtLogo2")) {
            if (this.hashMapCounter.containsKey("TxtLogo2")) {
                index = ((Integer) this.hashMapCounter.get("TxtLogo2")).intValue();
                this.editPhoto.replaceOverlayBitmap(stamp, index);
            } else {
                index = this.hashmapCounter + 1;
                this.hashmapCounter = index;
                this.hashMapCounter.put("TxtLogo2", Integer.valueOf(this.hashmapCounter));
                this.editPhoto.addOverlayBitmap(stamp);
            }
            this.arrayList.add(Integer.valueOf(index));
            this.txt2ArrayList.add(new DataClass(bmp, this.thirldLine));
        }
        this.editPhoto.invalidate();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        if (resultCode == -1) {
            switch (requestCode) {
                case 4:
                    if (data != null && data != null) {
                        int index = Integer.valueOf(data.getStringExtra("result")).intValue();
                        this.editPhoto.setImageBitmap(null);
                        this.editPhoto.destroyDrawingCache();
                        this.editPhoto.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), Constant.imageGallery[index]), screenWidth, screenHeight, true));
                        return;
                    }
                    return;
                case 45:
                    SaveToStorageUtil.deleteFolder();
                    return;
                default:
                    return;
            }
        }
    }

    private Bitmap readImages(String folder, String image) {
        try {
            Options options = new Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Config.RGB_565;
            options.inSampleSize = 2;
            return BitmapFactory.decodeStream(getAssets().open(folder + "/" + image), null, options);
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 || keyCode == 3) {
            this.editPhoto.removeAll();
            this.hashmapCounter = -1;
            this.hashMapCounter = new HashMap();
            this.arrayList.clear();
            this.redoArrayList.clear();
            this.framesBitmap.clear();
            this.txtArrayList.clear();
            this.txt1ArrayList.clear();
            this.txt2ArrayList.clear();
            this.redoFramesBitmap.clear();
            this.redoTxtArrayList.clear();
            this.redoTxt1ArrayList.clear();
            this.redoTxt2ArrayList.clear();
            this.colorCode = SupportMenu.CATEGORY_MASK;
            this.fontIndex = 2;
            finish();
        }
        return false;
    }

    public Bitmap textAsBitmap(String text, float textSize, int textColor) {
        EmbossMaskFilter filter = new EmbossMaskFilter(new float[]{1.0f, 1.0f, 0.0f}, 1.0f, 0.5f, 2.0f);
        Paint paint = new Paint();
        paint.setMaskFilter(filter);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Align.LEFT);
        paint.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + this.font[this.fontIndex] + ".ttf"));
        float baseline = -paint.ascent();
        Bitmap image = Bitmap.createBitmap((int) (paint.measureText(text) + 0.5f), (int) ((paint.descent() + baseline) + 0.5f), Config.ARGB_8888);
        new Canvas(image).drawText(text, 0.0f, baseline, paint);
        return image;
    }

    private void showColorPickerDialogDemo() {
        new ColorPickerDialog(this, SupportMenu.CATEGORY_MASK, new ColorPickerDialog.OnColorSelectedListener() {
            public void onColorSelected(int color) {
                boolean textFond = false;
                if (ActivitySolutioning.this.txtArrayList.size() > 0) {
                    ActivitySolutioning.this.firstLine = null;
                    ActivitySolutioning.this.secondLine = null;
                    ActivitySolutioning.this.thirldLine = null;
                    textFond = true;
                    ActivitySolutioning.this.firstLine = ((DataClass) ActivitySolutioning.this.txtArrayList.get(ActivitySolutioning.this.txtArrayList.size() - 1)).arrayListEditText;
                    if (ActivitySolutioning.this.txt1ArrayList.size() > 0) {
                        ActivitySolutioning.this.secondLine = ((DataClass) ActivitySolutioning.this.txt1ArrayList.get(ActivitySolutioning.this.txt1ArrayList.size() - 1)).arrayListEditText;
                    }
                    if (ActivitySolutioning.this.txt2ArrayList.size() > 0) {
                        ActivitySolutioning.this.thirldLine = ((DataClass) ActivitySolutioning.this.txt2ArrayList.get(ActivitySolutioning.this.txt2ArrayList.size() - 1)).arrayListEditText;
                    }
                    if (ActivitySolutioning.this.firstLine != null) {
                        ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.firstLine, 80.0f, color), "TxtLogo0");
                    }
                    if (ActivitySolutioning.this.secondLine != null) {
                        ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.secondLine, 80.0f, color), "TxtLogo1");
                    }
                    if (ActivitySolutioning.this.thirldLine != null) {
                        ActivitySolutioning.this.setTatooImage(ActivitySolutioning.this.textAsBitmap(ActivitySolutioning.this.thirldLine, 80.0f, color), "TxtLogo2");
                    }
                }
                ActivitySolutioning.this.colorCode = color;
                if (!textFond) {
                    Toast.makeText(ActivitySolutioning.this, "No Text Enter..", Toast.LENGTH_LONG).show();
                }
            }
        }).show();
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.nameEditText:
                this.nameEditText.setFocusable(true);
                this.nameEditText.setFocusableInTouchMode(true);
                break;
        }
        return false;
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.frameBgBitmap != null) {
            this.editPhoto.removeAll();
            this.editPhoto.setImageBitmap(this.frameBgBitmap);
            this.hashMapCounter.clear();
            this.txtArrayList.clear();
            this.txt1ArrayList.clear();
            this.txt2ArrayList.clear();
            this.redoTxtArrayList.clear();
            this.redoTxt1ArrayList.clear();
            this.redoTxt2ArrayList.clear();
            System.gc();
        }
    }

//    public void Google_Itrestial_Ads(final Context context) {
//
//
//        try {
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            final InterstitialAd interstitialAds = new InterstitialAd(context);
//            interstitialAds.setAdUnitId(getString(R.string.ads_inter));
//
//            interstitialAds.loadAd(adRequest);
//
//            interstitialAds.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//
//                    interstitialAds.show();
//
//                }
//
//                @Override
//                public void onAdClosed() {
//
//
//                }
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//
//                }
//            });
//        } catch (Exception e) {
//
//        }
//    }

}
