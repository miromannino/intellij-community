/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.python.packaging;

import com.intellij.openapi.projectRoots.Sdk;
import com.jetbrains.python.sdk.PythonSdkType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yole
 */
public class PyPackageManagersImpl extends PyPackageManagers {
  private final Map<String, PyPackageManagerImpl> myInstances = new HashMap<String, PyPackageManagerImpl>();

  @NotNull
  @Override
  public synchronized PyPackageManager forSdk(Sdk sdk) {
    final String name = sdk.getName();
    PyPackageManagerImpl manager = myInstances.get(name);
    if (manager == null) {
      if (PythonSdkType.isRemote(sdk)) {
        manager = new PyRemotePackageManagerImpl(sdk);
      }
      else {
        manager = new PyPackageManagerImpl(sdk);
      }
      myInstances.put(name, manager);
    }
    return manager;
  }
}
