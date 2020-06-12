package com.xincaidong.calendardemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private ImageView mIvLast;
  private TextView mTvTime;
  private ImageView mIvNext;
  private RecyclerView mRV;
  private CalendarListAdapter mAdapter;
  private ArrayList<DateEntity> mDateEntities;
  private String currentMonth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
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
    months.get(2).setSelect(true);
    months.get(2).setSelectStatus(DateEntity.START);
    months.get(3).setSelectStatus(DateEntity.RANGE);
    months.get(4).setSelectStatus(DateEntity.RANGE);
    months.get(5).setSelectStatus(DateEntity.RANGE);
    months.get(6).setSelectStatus(DateEntity.RANGE);
    months.get(7).setSelect(true);
    months.get(7).setSelectStatus(DateEntity.END);
    dateEntities.addAll(months);
    mAdapter.setList(dateEntities);
  }

  private void initView() {
    mIvLast = (ImageView) findViewById(R.id.ivLast);
    mIvLast.setOnClickListener(this);
    mTvTime = (TextView) findViewById(R.id.tvTime);
    mIvNext = (ImageView) findViewById(R.id.ivNext);
    mIvNext.setOnClickListener(this);
    mRV = (RecyclerView) findViewById(R.id.recycleView);
    mAdapter = new CalendarListAdapter();
    mRV.setLayoutManager(new GridLayoutManager(this, 7));
    mRV.setAdapter(mAdapter);
    if (mRV.getItemAnimator() != null) {
      ((SimpleItemAnimator) mRV.getItemAnimator()).setSupportsChangeAnimations(false);
    }
    mAdapter.setOnItemClickListener(
        new OnItemClickListener() {
          @Override
          public void onItemClick(
              @NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            DateEntity item = mAdapter.getItem(position);
            if (item != null) {
              Log.d(TAG, "onItemClick: a" + item.getDay());
            }
          }
        });
  }

  @Override
  public void onClick(View v) {

    ArrayList<DateEntity> dateEntities;
    switch (v.getId()) {
      case R.id.ivLast:
        currentMonth = DataUtils.getSomeMonthDay(mTvTime.getText().toString(), -1);
        mTvTime.setText(DataUtils.formatDate(currentMonth, "yyyy-MM"));
        dateEntities = new ArrayList<>();
        dateEntities.addAll(mDateEntities);
        dateEntities.addAll(DataUtils.getMonth(currentMonth));
        mAdapter.setList(dateEntities);
        break;
      case R.id.ivNext:
        currentMonth = DataUtils.getSomeMonthDay(mTvTime.getText().toString(), +1);
        mTvTime.setText(DataUtils.formatDate(currentMonth, "yyyy-MM"));
        dateEntities = new ArrayList<>();
        dateEntities.addAll(mDateEntities);
        dateEntities.addAll(DataUtils.getMonth(currentMonth));
        mAdapter.setList(dateEntities);
        break;
      default:
        break;
    }
    Log.d(TAG, "initData: " + currentMonth);
  }
}
