/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.io;

import ilarkesto.base.Bytes;
import ilarkesto.concurrent.ATask;
import static ilarkesto.io.IO.downloadUrlToFile;

/**
 *
 *
 */
public class DownloadTask extends ATask {

	private long totalBytes;
	private Bytes bytesDownloaded = new Bytes(0);

	// --- dependencies ---

	private String url;
	private String username;
	private String password;
	private String destinationPath;

    /**
     *
     * @param url
     * @param destinationPath
     */
    public DownloadTask(String url, String destinationPath) {
		this.url = url;
		this.destinationPath = destinationPath;
	}

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
		this.username = username;
	}

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
		this.password = password;
	}

	// --- ---

    /**
     *
     */
    
	@Override
	protected void perform() {
		downloadUrlToFile(url, destinationPath, username, password, new Observer());
	}

    /**
     *
     * @return
     */
    @Override
	public float getProgress() {
		long downloaded = bytesDownloaded.toLong();
		if (totalBytes > 0 && downloaded > 0) {
			return (float) downloaded / (float) totalBytes;
		} else {
			return super.getProgress();
		}
	}

    /**
     *
     * @return
     */
    @Override
	public String getProgressMessage() {
		return url + " (" + bytesDownloaded.toRoundedString() + ")";
	}

	class Observer implements IO.CopyObserver {

                @Override
		public void totalSizeDetermined(long bytes) {
			totalBytes = bytes;
		}

                @Override
		public void dataCopied(long bytes) {
			bytesDownloaded = new Bytes(bytesDownloaded.toLong() + bytes);
		}

                @Override
		public boolean isAbortRequested() {
			return DownloadTask.this.isAbortRequested();
		}

	}

}
