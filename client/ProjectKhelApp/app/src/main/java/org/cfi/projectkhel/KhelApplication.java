package org.cfi.projectkhel;

import android.app.Application;

import org.cfi.projectkhel.data.storage.FileStorageHandler;
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
  }
}
