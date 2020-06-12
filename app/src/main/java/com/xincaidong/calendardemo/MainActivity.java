package com.xincaidong.calendardemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private ImageView mIvLast;
  private TextView mTvTime;
  private ImageView mIvNext;
  private RecyclerView mRV;
  private EmployeePunchListAdapter mAdapter;
  private ArrayList<DateEntity> mDateEntities;
  private String currentMonth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    initData();
  }

  private void initData() {
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy年MM月");
    currentMonth = "" + today;
    mTvTime.setText(today.format(formatters));
    mDateEntities = new ArrayList<>();
    DateEntity entity;
    for (int i = 1; i < 8; i++) {
      entity = new DateEntity();
      switch (i) {
        case Calendar.SUNDAY:
          entity.day = "日";
          break;
        case Calendar.MONDAY:
          entity.day = "一";
          break;
        case Calendar.TUESDAY:
          entity.day = "二";
          break;
        case Calendar.WEDNESDAY:
          entity.day = "三";
          break;
        case Calendar.THURSDAY:
          entity.day = "四";
          break;
        case Calendar.FRIDAY:
          entity.day = "五";
          break;
        case Calendar.SATURDAY:
          entity.day = "六";
          break;
        default:
          break;
      }
      mDateEntities.add(entity);
    }
    ArrayList<DateEntity> dateEntities = new ArrayList<>();
    dateEntities.addAll(mDateEntities);
    dateEntities.addAll(DataUtils.getMonth("" + LocalDate.now()));
    mAdapter.setList(dateEntities);
  }

  private void initView() {
    mIvLast = (ImageView) findViewById(R.id.ivLast);
    mIvLast.setOnClickListener(this);
    mTvTime = (TextView) findViewById(R.id.tvTime);
    mIvNext = (ImageView) findViewById(R.id.ivNext);
    mIvNext.setOnClickListener(this);
    mRV = (RecyclerView) findViewById(R.id.recycleView);
    mAdapter = new EmployeePunchListAdapter();
    mRV.setLayoutManager(new GridLayoutManager(this, 7));
    mRV.setAdapter(mAdapter);
    if (mRV.getItemAnimator() != null) {
      ((SimpleItemAnimator) mRV.getItemAnimator()).setSupportsChangeAnimations(false);
    }
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
  }
}
