package com.cnu.lwm2m.client.models;

import java.util.Arrays;
import java.util.List;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.impl.kepco.AMIServerInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KepcoServer extends BaseInstanceEnabler {

	ObjectExcuteTask objectTask;
	private final static List<Integer> supportedResources = Arrays.asList(0, 1, 10, 11, 15, 16, 20, 21, 25, 26, 30, 31, 35, 36);

	private String bootstrapIp;
	private int bootstrapPort;
	private String securityIp;
	private int securityPort;
	private String securityProxyIp;
	private int securityProxyPort;
	private String serverIp;
	private int serverPort;
	private String proxyServerIp;
	private int proxyServerPort;
	private String dlmsMasterIp;
	private int dlmsMasterPort;
	private String dlmsManagerIp;
	private int dlmsManagerPort;

	AMIServerInfo server;

	public KepcoServer() {
		// should only be used at bootstrap time
	}

	public KepcoServer(AMIServerInfo server, ObjectExcuteTask task) {
		// should only be used at bootstrap time
		this.server = server;
		bootstrapIp = server.getBootstrapIp();
		bootstrapPort = server.getBootstrapPort();
		securityIp = server.getSecurityIp();
		securityPort = server.getSecurityPort();
		securityProxyIp = server.getSecurityProxyIp();
		securityProxyPort = server.getSecurityProxyPort();
		serverIp = server.getServerIp();
		serverPort = server.getServerPort();
		proxyServerIp = server.getProxyServerIp();
		proxyServerPort = server.getProxyServerPort();
		dlmsMasterIp = server.getDlmsMasterIp();
		dlmsMasterPort = server.getDlmsMasterPort();
		dlmsManagerIp = server.getDlmsManagerIp();
		dlmsManagerPort = server.getDlmsManagerPort();
		this.objectTask = task;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			return ReadResponse.success(resourceid, bootstrapIp);

		case 1:
			return ReadResponse.success(resourceid, bootstrapPort);

		case 10:
			return ReadResponse.success(resourceid, securityIp);

		case 11:
			return ReadResponse.success(resourceid, securityPort);

		case 15:
			return ReadResponse.success(resourceid, securityProxyIp);

		case 16:
			return ReadResponse.success(resourceid, securityProxyPort);

		case 20:
			return ReadResponse.success(resourceid, serverIp);

		case 21:
			return ReadResponse.success(resourceid, serverPort);

		case 25:
			return ReadResponse.success(resourceid, proxyServerIp);

		case 26:
			return ReadResponse.success(resourceid, proxyServerPort);

		case 30:
			return ReadResponse.success(resourceid, dlmsMasterIp);

		case 31:
			return ReadResponse.success(resourceid, dlmsMasterPort);

		case 35:
			return ReadResponse.success(resourceid, dlmsManagerIp);

		case 36:
			return ReadResponse.success(resourceid, dlmsManagerPort);

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		if (!identity.isSystem()) {
			log.debug("Write on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0:
			bootstrapIp = (String) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 1:
			bootstrapPort = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 10:
			securityIp = (String) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 11:
			securityPort = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 15:
			securityProxyIp = (String) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 16:
			securityProxyPort = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 20:
			serverIp = (String) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 21:
			serverPort = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 25:
			proxyServerIp = (String) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 26:
			proxyServerPort = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 30:
			dlmsMasterIp = (String) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 31:
			dlmsMasterPort = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 35:
			dlmsManagerIp = (String) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		case 36:
			dlmsManagerPort = (int) value.getValue();
			fireResourcesChange(resourceid);

			return WriteResponse.success();
		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		log.debug("Execute on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);

		return super.execute(identity, resourceid, params);
	}

	@Override
	public void reset(int resourceid) {
		switch (resourceid) {
		default:
			super.reset(resourceid);
		}
	}

	@Override
	public List<Integer> getAvailableResourceIds(ObjectModel model) {
		return supportedResources;
	}
}
