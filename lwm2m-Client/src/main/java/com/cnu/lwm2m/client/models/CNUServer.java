package com.cnu.lwm2m.client.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

import com.cnu.lwm2m.client.init.task.ObjectExcuteTask;
import com.cnu.lwm2m.client.models.impl.oma.ServerInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CNUServer extends BaseInstanceEnabler {

	ObjectExcuteTask objectTask;
	private final static List<Integer> supportedResources = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	private Map<Integer, Type> typeMap;

	private int shortServerId;
	private long lifeTime;
	private Long defaultMinPeriod;
	private Long defaultMaxPeriod;
	private BindingMode binding;
	private boolean notifyWhenDisable;
	private long disableTimeout;

	public CNUServer() {
		// should only be used at bootstrap time
		typeMap = new HashMap<Integer, Type>();
		typeMap.put(0, Type.INTEGER);
		typeMap.put(1, Type.INTEGER);
		typeMap.put(2, Type.INTEGER);
		typeMap.put(3, Type.INTEGER);
		typeMap.put(5, Type.INTEGER);
		typeMap.put(6, Type.BOOLEAN);
		typeMap.put(7, Type.STRING);
	}

	public CNUServer(ServerInfo server, ObjectExcuteTask task) {
		this();
		// should only be used at bootstrap time
		this.shortServerId = server.getServerID();
		this.lifeTime = server.getLifeTime();
		this.binding = server.getBinding();
		this.notifyWhenDisable = server.isNotifyWhenDisable();
		this.defaultMinPeriod = server.getDefaultMinPeriod();
		this.defaultMaxPeriod = server.getDefaultMaxPeriod();
		this.disableTimeout = server.getDisableTimeout();
		this.objectTask = task;
	}

	@Override
	public ReadResponse read(ServerIdentity identity, int resourceid) {
		if (!identity.isSystem()) {
			log.debug("Read on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		switch (resourceid) {
		case 0: // short server ID
			return ReadResponse.success(resourceid, shortServerId);

		case 1: // lifetime
			return ReadResponse.success(resourceid, lifeTime);

		case 2: // default min period
			if (null == defaultMinPeriod)
				return ReadResponse.notFound();
			return ReadResponse.success(resourceid, defaultMinPeriod);

		case 3: // default max period
			if (null == defaultMaxPeriod)
				return ReadResponse.notFound();
			return ReadResponse.success(resourceid, defaultMaxPeriod);

		case 5: // Disable Timeout
			return ReadResponse.success(resourceid, disableTimeout);

		case 6: // notification storing when disable or offline
			return ReadResponse.success(resourceid, notifyWhenDisable);

		case 7: // binding
			return ReadResponse.success(resourceid, binding.toString());

		default:
			return super.read(identity, resourceid);
		}
	}

	@Override
	public WriteResponse write(ServerIdentity identity, int resourceid, LwM2mResource value) {
		if (!identity.isSystem()) {
			log.debug("Write on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);
		}

		if (value.getType() != typeMap.get(resourceid)) {
			return WriteResponse.badRequest("invalid type : " + value.getType());
		}

		switch (resourceid) {
		case 0: // short server ID
			int previousShortServerId = shortServerId;
			shortServerId = ((Long) value.getValue()).intValue();

			if (previousShortServerId != shortServerId) {
				fireResourcesChange(resourceid);
			}

			return WriteResponse.success();

		case 1: // lifetime
			long previousLifetime = lifeTime;
			lifeTime = (Long) value.getValue();
			if (previousLifetime != lifeTime)
				fireResourcesChange(resourceid);
			return WriteResponse.success();

		case 2: // default min period
			Long previousDefaultMinPeriod = defaultMinPeriod;
			defaultMinPeriod = (Long) value.getValue();

			if (!Objects.equals(previousDefaultMinPeriod, defaultMinPeriod)) {
				fireResourcesChange(resourceid);
			}

			return WriteResponse.success();

		case 3: // default max period
			Long previousDefaultMaxPeriod = defaultMaxPeriod;
			defaultMaxPeriod = (Long) value.getValue();

			if (!Objects.equals(previousDefaultMaxPeriod, defaultMaxPeriod)) {
				fireResourcesChange(resourceid);
			}

			return WriteResponse.success();

		case 5: // Disable Timeout
			long prevDisabletimeout = disableTimeout;
			log.info("disable time : {}", value.getValue());
			disableTimeout = (long) value.getValue();

			if (prevDisabletimeout != disableTimeout) {
				fireResourcesChange(resourceid);
			}

			return WriteResponse.success();

		case 6: // notification storing when disable or offline
			boolean previousNotifyWhenDisable = notifyWhenDisable;
			notifyWhenDisable = (boolean) value.getValue();

			if (previousNotifyWhenDisable != notifyWhenDisable) {
				fireResourcesChange(resourceid);
			}

			return WriteResponse.success();

		case 7: // binding
			try {
				BindingMode previousBinding = binding;
				binding = BindingMode.valueOf((String) value.getValue());

				if (!Objects.equals(previousBinding, binding)) {
					fireResourcesChange(resourceid);
				}

				return WriteResponse.success();
			} catch (IllegalArgumentException e) {
				return WriteResponse.badRequest("invalid value : " + value.getType());
			}

		default:
			return super.write(identity, resourceid, value);
		}
	}

	@Override
	public ExecuteResponse execute(ServerIdentity identity, int resourceid, String params) {
		log.debug("Execute on Server resource /{}/{}/{}", getModel().id, getId(), resourceid);

		log.info("Object Execute Start!!");
		if (resourceid == 4) {
			objectTask.asyncServerDisableExecutor(getLwM2mClient(), (int) disableTimeout);
			return ExecuteResponse.success();
		} else if (resourceid == 8) {
			getLwM2mClient().triggerRegistrationUpdate(identity);
			return ExecuteResponse.success();
		} else {
			return super.execute(identity, resourceid, params);
		}
	}

	@Override
	public void reset(int resourceid) {
		switch (resourceid) {
		case 2:
			defaultMinPeriod = null;
			break;
		case 3:
			defaultMaxPeriod = null;
			break;
		default:
			super.reset(resourceid);
		}
	}

	@Override
	public List<Integer> getAvailableResourceIds(ObjectModel model) {
		return supportedResources;
	}
}
