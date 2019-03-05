package net.minepos.plugin.implementations.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import net.minepos.plugin.core.enums.Registerables;
import net.minepos.plugin.core.managers.APIParamManager;
import net.minepos.plugin.framework.APIParam;
import net.minepos.plugin.framework.Registerable;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class APIParamsRegisterable extends Registerable {
    @Inject @Named("Reflections") private Reflections reflections;
    @Inject private Injector injector;
    @Inject private APIParamManager paramManager;

    public APIParamsRegisterable() {
        super(Registerables.API_PARAMS);
    }

    @Override
    protected void execute() {
        reflections.getSubTypesOf(APIParam.class).stream().map(injector::getInstance).forEach(paramManager::put);
    }
}
