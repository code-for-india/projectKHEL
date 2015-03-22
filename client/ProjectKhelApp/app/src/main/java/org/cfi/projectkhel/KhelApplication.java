package org.cfi.projectkhel;

import android.app.Application;

import org.cfi.projectkhel.data.DataManager;
import org.cfi.projectkhel.data.DataStorage;
import org.cfi.projectkhel.data.storage.FileDataStorage;
import org.cfi.projectkhel.data.storage.FileStorageHandler;
import org.cfi.projectkhel.data.storage.TestDataStorage;
import org.cfi.projectkhel.rest.MasterDataFetcher;

/**
 * Maintains the application wide instances.
 */
public class KhelApplication extends Application {

  private MasterDataFetcher dataFetcher;

  private FileStorageHandler storageHandler;

  public MasterDataFetcher getDataFetcher() {
    return dataFetcher;
  }

  public FileStorageHandler getStorageHandler() {
    return storageHandler;
  }

  public void setStorageHandler(FileStorageHandler storageHandler) {
    this.storageHandler = storageHandler;
    this.dataFetcher = new MasterDataFetcher(storageHandler);

//    DataStorage storage = new TestDataStorage();
    final DataStorage storage = new FileDataStorage(storageHandler);
    DataManager.getInstance().initialize(storage);
  }
}
