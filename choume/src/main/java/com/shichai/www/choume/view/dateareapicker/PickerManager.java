package com.shichai.www.choume.view.dateareapicker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.globalways.choume.R;
import com.shichai.www.choume.view.dateareapicker.wheelcity.AddressData;
import com.shichai.www.choume.view.dateareapicker.wheelcity.OnWheelChangedListener;
import com.shichai.www.choume.view.dateareapicker.wheelcity.WheelView;
import com.shichai.www.choume.view.dateareapicker.wheelcity.adapters.AbstractWheelTextAdapter;
import com.shichai.www.choume.view.dateareapicker.wheelcity.adapters.ArrayWheelAdapter;
import com.shichai.www.choume.view.dateareapicker.wheelview.JudgeDate;
import com.shichai.www.choume.view.dateareapicker.wheelview.ScreenInfo;
import com.shichai.www.choume.view.dateareapicker.wheelview.WheelMain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wyp on 15/12/28.
 */
public class PickerManager {

    private static Context context;

    public PickerManager(Context context) {
        this.context = context;
    }


    public AreaPicker initAreaPicker(){
        return new AreaPicker();
    }

    public DatePicker initDatePicker(){
        return new DatePicker();
    }


    public static class AreaPicker{
        private String areaStr;
        public String getAreaStr() {
            return areaStr;
        }

        private AreaPicker(){}

        public View getAreaView() {
            View contentView = LayoutInflater.from(context).inflate(
                    R.layout.dateareapicker_wheelcity_cities_layout, null);
            final WheelView country = (WheelView) contentView
                    .findViewById(R.id.wheelcity_country);
            country.setVisibleItems(3);
            country.setViewAdapter(new CountryAdapter(context));

            final String cities[][] = AddressData.CITIES;
            final String ccities[][][] = AddressData.COUNTIES;
            final WheelView city = (WheelView) contentView
                    .findViewById(R.id.wheelcity_city);
            city.setVisibleItems(0);

            // 地区选择
            final WheelView ccity = (WheelView) contentView
                    .findViewById(R.id.wheelcity_ccity);
            ccity.setVisibleItems(0);// 不限城市

            country.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    updateCities(city, cities, newValue);
                    areaStr = AddressData.PROVINCES[country.getCurrentItem()]
                            + " | "
                            + AddressData.CITIES[country.getCurrentItem()][city
                            .getCurrentItem()]
                            + " | "
                            + AddressData.COUNTIES[country.getCurrentItem()][city
                            .getCurrentItem()][ccity.getCurrentItem()];
                }
            });

            city.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    updatecCities(ccity, ccities, country.getCurrentItem(),
                            newValue);
                    areaStr = AddressData.PROVINCES[country.getCurrentItem()]
                            + " | "
                            + AddressData.CITIES[country.getCurrentItem()][city
                            .getCurrentItem()]
                            + " | "
                            + AddressData.COUNTIES[country.getCurrentItem()][city
                            .getCurrentItem()][ccity.getCurrentItem()];
                }
            });

            ccity.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    areaStr = AddressData.PROVINCES[country.getCurrentItem()]
                            + " | "
                            + AddressData.CITIES[country.getCurrentItem()][city
                            .getCurrentItem()]
                            + " | "
                            + AddressData.COUNTIES[country.getCurrentItem()][city
                            .getCurrentItem()][ccity.getCurrentItem()];
                }
            });

            country.setCurrentItem(1);// 设置北京
            city.setCurrentItem(1);
            ccity.setCurrentItem(1);
            return contentView;
        }


        /**
         * Updates the city wheel
         */
        private void updateCities(WheelView city, String cities[][], int index) {
            ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context,
                    cities[index]);
            adapter.setTextSize(18);
            city.setViewAdapter(adapter);
            city.setCurrentItem(0);
        }

        /**
         * Updates the ccity wheel
         */
        private void updatecCities(WheelView city, String ccities[][][], int index,
                                   int index2) {
            ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context,
                    ccities[index][index2]);
            adapter.setTextSize(18);
            city.setViewAdapter(adapter);
            city.setCurrentItem(0);
        }
        /**
         * Adapter for countries
         */
        private class CountryAdapter extends AbstractWheelTextAdapter {
            // Countries names
            private String countries[] = AddressData.PROVINCES;

            /**
             * Constructor
             */
            protected CountryAdapter(Context context) {
                super(context, R.layout.dateareapicker_wheelcity_country_layout, NO_RESOURCE);
                setItemTextResource(R.id.wheelcity_country_name);
            }

            @Override
            public View getItem(int index, View cachedView, ViewGroup parent) {
                View view = super.getItem(index, cachedView, parent);
                return view;
            }

            @Override
            public int getItemsCount() {
                return countries.length;
            }

            @Override
            protected CharSequence getItemText(int index) {
                return countries[index];
            }
        }
    }




    public static class DatePicker{
        private View timePickerView;
        private String initDate = "";
        private WheelMain wheelMain;

        private DatePicker(){}

        public View getDatePickerView(String initDate){
            LayoutInflater inflater1 = LayoutInflater.from(context);
            timePickerView = inflater1.inflate(R.layout.dateareapicker_timepicker, null);
            setInitDate(initDate);
            return timePickerView;
        }

        public void setInitDate(String date){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ScreenInfo screenInfo1 = new ScreenInfo((Activity)context);
            wheelMain = new WheelMain(timePickerView);
            wheelMain.screenheight = screenInfo1.getHeight();
            Calendar initCalendar = Calendar.getInstance();
            if (JudgeDate.isDate(date, "yyyy-MM-dd")) {
                try {
                    initCalendar.setTime(dateFormat.parse(date));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            int year1 = initCalendar.get(Calendar.YEAR);
            int month1 = initCalendar.get(Calendar.MONTH);
            int day1 = initCalendar.get(Calendar.DAY_OF_MONTH);
            wheelMain.initDateTimePicker(year1, month1, day1);
        }

        public String getCurrentDate(){
            return wheelMain.getTime();
        }
    }
}
