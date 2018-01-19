package com.cxg.core.util.format;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "unchecked", "serial" })
public final class LockMode implements Serializable {
	private final int level;
	private final String name;
	@SuppressWarnings("rawtypes")
	private static final Map INSTANCES = new HashMap();

	public static final LockMode NONE = new LockMode(0, "NONE");

	public static final LockMode READ = new LockMode(5, "READ");

	public static final LockMode UPGRADE = new LockMode(10, "UPGRADE");

	public static final LockMode UPGRADE_NOWAIT = new LockMode(10,
			"UPGRADE_NOWAIT");

	public static final LockMode WRITE = new LockMode(10, "WRITE");

	public static final LockMode FORCE = new LockMode(15, "FORCE");

	static {
		INSTANCES.put(NONE.name, NONE);
		INSTANCES.put(READ.name, READ);
		INSTANCES.put(UPGRADE.name, UPGRADE);
		INSTANCES.put(UPGRADE_NOWAIT.name, UPGRADE_NOWAIT);
		INSTANCES.put(WRITE.name, WRITE);
		INSTANCES.put(FORCE.name, FORCE);
	}

	private LockMode(int level, String name) {
		this.level = level;
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public boolean greaterThan(LockMode mode) {
		return this.level > mode.level;
	}

	public boolean lessThan(LockMode mode) {
		return this.level < mode.level;
	}

	private Object readResolve() {
		return parse(this.name);
	}

	public static LockMode parse(String name) {
		return (LockMode) INSTANCES.get(name);
	}
}
