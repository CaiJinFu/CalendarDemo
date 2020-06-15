package com.xincaidong.calendardemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.xincaidong.calendardemo.databinding.ActivityMainBinding;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private TextView mTvTime;
  private RecyclerView mRV;
  private CalendarListAdapter mAdapter;
  private ArrayList<DateEntity> mDateEntities;
  private String currentMonth;
  private ActivityMainBinding mInflate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutInflater from = LayoutInflater.from(this);
    mInflate = ActivityMainBinding.inflate(from);
    setContentView(mInflate.getRoot());
    initView();
    initData();
  }

  private static final String TAG = "MainActivity";

  private void initData() {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
    currentMonth = "" + today;
    Log.d(TAG, "initData: " + currentMonth);
    mTvTime.setText(today.format(formatters));
    mDateEntities = new ArrayList<>();
    DateEntity entity;
    for (int i = 1; i < 8; i++) {
      entity = new DateEntity();
      switch (i) {
        case Calendar.SUNDAY:
          entity.setDay("日");
          break;
        case Calendar.MONDAY:
          entity.setDay("一");
          break;
        case Calendar.TUESDAY:
          entity.setDay("二");
          break;
        case Calendar.WEDNESDAY:
          entity.setDay("三");
          break;
        case Calendar.THURSDAY:
          entity.setDay("四");
          break;
        case Calendar.FRIDAY:
          entity.setDay("五");
          break;
        case Calendar.SATURDAY:
          entity.setDay("六");
          break;
        default:
          break;
      }
      mDateEntities.add(entity);
    }
    ArrayList<DateEntity> dateEntities = new ArrayList<>();
    dateEntities.addAll(mDateEntities);
    ArrayList<DateEntity> months = DataUtils.getMonth("" + LocalDate.now());
    // months.get(2).setSelect(true);
    // months.get(2).setSelectStatus(DateEntity.START);
    // months.get(3).setSelectStatus(DateEntity.RANGE);
    // months.get(4).setSelectStatus(DateEntity.RANGE);
    // months.get(5).setSelectStatus(DateEntity.RANGE);
    // months.get(6).setSelectStatus(DateEntity.RANGE);
    // months.get(7).setSelect(true);
    // months.get(7).setSelectStatus(DateEntity.END);
    dateEntities.addAll(months);
    mAdapter.setList(dateEntities);
  }

  private void initView() {
    mInflate.ivLast.setOnClickListener(this);
    mInflate.ivNext.setOnClickListener(this);
    mInflate.btn.setOnClickListener(this);
    mTvTime = mInflate.tvTime;
    mRV = mInflate.recycleView;
    mAdapter = new CalendarListAdapter();
    mRV.setLayoutManager(new GridLayoutManager(this, 7));
    mRV.setAdapter(mAdapter);
    if (mRV.getItemAnimator() != null) {
      ((SimpleItemAnimator) mRV.getItemAnimator()).setSupportsChangeAnimations(false);
    }
    mAdapter.setOnItemClickListener((adapter, view, position) -> {});
  }

  @Override
  public void onClick(View v) {
    ArrayList<DateEntity> dateEntities;
    switch (v.getId()) {
      case R.id.ivLast:
        // 上个月
        currentMonth = DataUtils.getSomeMonthDay(mTvTime.getText().toString(), -1);
        mTvTime.setText(DataUtils.formatDate(currentMonth, "yyyy-MM"));
        dateEntities = new ArrayList<>();
        dateEntities.addAll(mDateEntities);
        dateEntities.addAll(DataUtils.getMonth(currentMonth));
        mAdapter.setList(dateEntities);
        break;
      case R.id.ivNext:
        // 下个月
        currentMonth = DataUtils.getSomeMonthDay(mTvTime.getText().toString(), +1);
        mTvTime.setText(DataUtils.formatDate(currentMonth, "yyyy-MM"));
        dateEntities = new ArrayList<>();
        dateEntities.addAll(mDateEntities);
        dateEntities.addAll(DataUtils.getMonth(currentMonth));
        mAdapter.setList(dateEntities);
        break;
      case R.id.btn:
        // 获取开始结束时间
        HashMap<Integer, DateEntity> hashMap = mAdapter.getHashMap();
        String start = "";
        String end = "";
        DateEntity entityStart = hashMap.get(DateEntity.START);
        if (entityStart != null) {
          long million = entityStart.getMillion();
          start = DataUtils.formatDateTime(million);
        }
        DateEntity dateEntity = hashMap.get(DateEntity.END);
        if (dateEntity != null) {
          long million = dateEntity.getMillion();
          end = DataUtils.formatDateTime(million);
        }
        mInflate.tvShowTime.setText("开始时间为：" + start + ",结束时间为：" + end);
        break;
      default:
        break;
    }
    Log.d(TAG, "initData: " + currentMonth);
  }
}
