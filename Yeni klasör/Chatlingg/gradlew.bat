<?xml version="1.0" encoding="UTF-8"?>
<root>
  <item name="android.telephony.CarrierConfigManager">
    <annotation name="android.support.annotation.SystemService">
      <val name="value" val="android.content.Context.CARRIER_CONFIG_SERVICE" />
    </annotation>
  </item>
  <item name="android.telephony.CarrierConfigManager android.os.PersistableBundle getConfig()">
    <annotation name="android.support.annotation.Nullable" />
  </item>
  <item name="android.telephony.CarrierConfigManager android.os.PersistableBundle getConfigForSubId(int)">
    <annotation name="android.support.annotation.Nullable" />
  </item>
  <item name="android.telephony.CellIdentity java.lang.CharSequence getOperatorAlphaLong()">
    <annotation name="android.support.annotation.Nullable" />
  </item>
  <item name="android.telephony.CellIdentity java.lang.CharSequence getOperatorAlphaShort()">
    <annotation name="android.support.annotation.Nullable" />
  </item>
  <item name="android.telephony.CellIdentity void writeToParcel(android.os.Parcel, int)">
    <annotation name="android.support.annotation.CallSuper" />
  </item>
  <item name="android.telephony.CellInfo int getCellConnectionStatus()">
    <annotation name="android.support.annotation.IntDef">
      <val name="value" val="{android.telephony.CellInfo.CONNECTION_NONE, android.telephony.CellInfo.CONNECTION_PRIMARY_SERVING, android.telephony.CellInfo.CONNECTION_SECONDARY_SERVING, android.telephony.CellInfo.CONNECTION_UNKNOWN}" />
    </annotation>
  </item>
  <item name="android.telephony.MbmsDownloadSession android.telephony.MbmsDownloadSession create(android.content.Context, java.util.concurrent.Executor, android.telephony.mbms.MbmsDownloadSessionCallback) 0">
    <annotation name="android.support.annotation.NonNull" />
  </item>
  <item name="android.telephony.MbmsDownloadSession android.telephony.MbmsDownloadSession create(android.content.Context, java.util.concurrent.Executor, android.telephony.mbms.MbmsDownloadSessionCallback) 1">
    <annotation name="android.support.annotation.NonNull" />
  </item>
  <item name="android.telephony.MbmsDownloadSession android.telephony.MbmsDownloadSession create(android.content.Context, java.util.concurrent.Executor, android.telephony.mbms.MbmsDownloadS