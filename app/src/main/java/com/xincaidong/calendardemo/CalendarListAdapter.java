package com.xincaidong.calendardemo;

import android.text.TextUtils;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class CalendarListAdapter extends BaseQuickAdapter<DateEntity, BaseViewHolder> {

  public CalendarListAdapter() {
    super(R.layout.recycler_item_calendar_list);
  }

  @Override
  protected void convert(BaseViewHolder helper, DateEntity item) {
    TextView tvDay = helper.getView(R.id.tvDay);
    if (!TextUtils.isEmpty(item.getDay())) {
      //0开头的要去掉，比如01.只要显示1
      if (item.getDay().startsWith("0")) {
        String replace = item.getDay().replace("0", "");
        tvDay.setText(replace);
      } else {
        tvDay.setText(item.getDay());
      }
    }
    if (item.isSelect()) {
      tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.color_white));
      tvDay.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_select_day));
    }
    TextView tvLeftBg = helper.getView(R.id.tvLeftBg);
    TextView tvRightBg = helper.getView(R.id.tvRightBg);
    switch (item.getSelectStatus()) {
      case DateEntity.START:
        // 选择日期开始
        tvLeftBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_white));
        tvRightBg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_FFF0F0));
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
