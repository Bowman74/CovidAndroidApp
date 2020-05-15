package com.magenic.covid_tracker;

public class IsoInfo {

    public String _isoCode = "";
    public String _region = "";

    public IsoInfo(String isoCode, String region) {
        this._isoCode = isoCode;
        this._region = region;
    }

    public String get_isoCode() {
        return _isoCode;
    }

    public void set_isoCode(String _isoCode) {
        this._isoCode = _isoCode;
    }

    public String get_region() {
        return _region;
    }

    public void set_region(String _region) {
        this._region = _region;
    }
}
