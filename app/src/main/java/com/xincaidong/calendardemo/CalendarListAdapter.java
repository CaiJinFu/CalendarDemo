package com.xincaidong.calendardemo;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;

public class CalendarListAdapter extends BaseQuickAdapter<DateEntity, BaseViewHolder> {

  public CalendarListAdapter() {
    super(R.layout.recycler_item_calendar_list);
  }

  private HashMap<Integer, DateEntity> mHashMap = new HashMap<>();

  public void setNewData() {}

  @Override
  protected void setOnItemClick(@NotNull View v, int position) {
    super.setOnItemClick(v, position);
    DateEntity item = getItem(position);
    if (item != null) {
      if (item.getMillion() == 0) {
        return;
      }
      if (item.isSelect()) {
        // 如果是已经选中过的日期，那么将重置所有的状态，再将此日期作为开始的时间
        resetStartSelect(item);
      } else {
        // 如果已经选择了开始时间与结束时间，那么不管点击的是什么，都将重置成开始时间
        if (mHashMap.get(DateEntity.START) != null && mHashMap.get(DateEntity.END) != null) {
          // 此日期是在选中的日期区间里面
          resetStartSelect(item);
          return;
        }
        // 如果没有开始时间也没有结束时间，那么将设置开始时间
        if (mHashMap.get(DateEntity.START) == null && mHashMap.get(DateEntity.END) == null) {
          // 此日期是在选中的日期区间里面
          resetStartSelect(item);
          return;
        }
        // 有开始时间，需判断点击的时间是否在开始时间之后，如果不在则重置开始时间，在则当成结束时间
        if (mHashMap.get(DateEntity.START) != null) {
          DateEntity entityStart = mHashMap.get(DateEntity.START);
          long startMillion = entityStart.getMillion();
          long million = item.getMillion();
          if (DataUtils.after(startMillion, million)) {
            // item.setSelect(true);
            // item.setSelectStatus(DateEntity.END);
            mHashMap.put(DateEntity.END, item);
            notifyDataSetChanged();
            return;
          }
        }
        resetStartSelect(item);
      }
      Log.d(TAG, "onItemClick: a" + item.getDay());
    }
    Log.d(TAG, "onItemClick: a" + position);
  }

  private void resetStartSelect(DateEntity item) {
    if (mHashMap.size() > 0) {
      mHashMap.clear();
    }
    for (DateEntity datum : getData()) {
      datum.setSelect(false);
      datum.setSelectStatus(DateEntity.NORMAL);
    }
    item.setSelect(true);
    item.setSelectStatus(DateEntity.START);
    mHashMap.put(DateEntity.START, item);
    notifyDataSetChanged();
  }

  private static final String TAG = "CalendarListAdapter";

  @Override
  protected void convert(BaseViewHolder helper, DateEntity item) {
    TextView tvDay = helper.getView(R.id.tvDay);
    TextView tvLeftBg = helper.getView(R.id.tvLeftBg);
    TextView tvRightBg = helper.getView(R.id.tvRightBg);
    if (!TextUtils.isEmpty(item.getDay())) {
      // 0开头的要去掉，比如01.只要显示1
      if (item.getDay().startsWith("0")) {
        String replace = item.getDay().replace("0", "");
        tvDay.setText(replace);
      } else {
        tvDay.setText(item.getDay());
      }
    }
    long million = item.getMillion();
    if (million == 0) {
      // 顶部标识星期日，星期一文字提示或者是空的日期填充需要单独处理
      item.setSelect(false);
      item.setSelectStatus(DateEntity.NORMAL);
      tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
      tvDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
      tvLeftBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
      tvRightBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
      return;
    }
    if (mHashMap.get(DateEntity.START) != null && mHashMap.get(DateEntity.END) != null) {
      // 有开始和结束时间
      DateEntity entityStart = mHashMap.get(DateEntity.START);
      DateEntity entityEnd = mHashMap.get(DateEntity.END);
      long startMillion = entityStart.getMillion();
      long endMillion = entityEnd.getMillion();
      if (startMillion == million) {
        // 是开始日期
        item.setSelect(true);
        item.setSelectStatus(DateEntity.START);
      }
      if (endMillion == million) {
        // 是结束日期
        item.setSelect(true);
        item.setSelectStatus(DateEntity.END);
      }
      if (startMillion != million && endMillion != million) {
        if (DataUtils.after(startMillion, million) && DataUtils.before(million, endMillion)) {
          // 此日期是在选中的日期区间里面
          item.setSelect(false);
          item.setSelectStatus(DateEntity.RANGE);
        } else {
          // 此日期不在选中的区间
          item.setSelect(false);
          item.setSelectStatus(DateEntity.NORMAL);
        }
      }
    } else if (mHashMap.get(DateEntity.START) == null && mHashMap.get(DateEntity.END) == null) {
      // 没有选择日期
      item.setSelect(false);
      item.setSelectStatus(DateEntity.NORMAL);
    } else {
      // 只选择了开始日期
      DateEntity entityStart = mHashMap.get(DateEntity.START);
      long startMillion = entityStart.getMillion();
      if (startMillion == million) {
        item.setSelect(true);
        item.setSelectStatus(DateEntity.START);
      } else if (mHashMap.get(DateEntity.END) != null) {
        DateEntity entityEnd = mHashMap.get(DateEntity.END);
        long endMillion = entityEnd.getMillion();
        if (endMillion == million) {
          item.setSelect(true);
          item.setSelectStatus(DateEntity.END);
        } else {
          item.setSelect(false);
          item.setSelectStatus(DateEntity.NORMAL);
        }
      }
    }

    if (item.isSelect()) {
      tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.color_white));
      tvDay.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_select_day));
    }

    switch (item.getSelectStatus()) {
      case DateEntity.START:
        // 选择日期开始
        DateEntity entity = mHashMap.get(DateEntity.END);
        if (entity != null) {
          tvLeftBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
          tvRightBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_FFF0F0));
        } else {
          tvLeftBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
          tvRightBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
        }
        break;
      case DateEntity.END:
        // 选择日期结束
        tvLeftBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_FFF0F0));
        tvRightBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
        break;
      case DateEntity.RANGE:
        // 选择日期开始到结束的范围内的日期
        tvLeftBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_FFF0F0));
        tvRightBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_FFF0F0));
        tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
        tvDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_FFF0F0));
        break;
      case DateEntity.NORMAL:
        // 不在选中范围内日期
        tvLeftBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
        tvRightBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
        break;
      default:
        // 日期默认显示的效果
        tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.color_999999));
        tvDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
        break;
    }
  }
}
