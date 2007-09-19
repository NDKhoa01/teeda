package examples.teeda.web.download;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.FileUtil;
import org.seasar.teeda.core.exception.AppFacesException;
import org.seasar.teeda.core.util.CancelUtil;
import org.seasar.teeda.extension.annotation.validator.Required;

import examples.teeda.helper.DownloadHelper;

public class CsvdownloadPage {

	private HttpServletResponse response;

	private FacesContext facesContext;

	private DownloadHelper downloadHelper;

	@Required
	private String filename;

	public void doDownload() throws IOException {
		String name = filename;
		if (!filename.endsWith(".csv")) {
			name = filename + ".csv";
		}
		final File file = downloadHelper.getFile(name);
		response.setContentType("application/octet-stream");
		response.setHeader("content-disposition", "attachment; filename=\""
				+ file.getName() + "\"");
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(FileUtil.getBytes(file));
		} catch (IOException e) {
			Throwable cause = e.getCause();
			if (!CancelUtil.isCancelled(cause)) {
				throw new AppFacesException("E0000002");
			} else {
				System.out.println("user cancelled....");
			}
		} finally {
			try {
				os.close();
			} catch (final IOException e) {
			}
		}
		this.facesContext.responseComplete();
	}

	public Class initialize() {
		return null;
	}

	public Class prerender() {
		return null;
	}

	public DownloadHelper getDownloadHelper() {
		return downloadHelper;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setDownloadHelper(DownloadHelper downloadHelper) {
		this.downloadHelper = downloadHelper;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
