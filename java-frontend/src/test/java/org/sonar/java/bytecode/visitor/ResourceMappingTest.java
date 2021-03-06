/*
 * SonarQube Java
 * Copyright (C) 2012-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.bytecode.visitor;

import org.junit.Test;
import org.sonar.api.resources.File;

import static org.fest.assertions.Assertions.assertThat;

public class ResourceMappingTest {

  @Test
  public void testGetComponentKeyByFileKey() {
    ResourceMapping resourceMapping = new ResourceMapping();
    File resource = File.create("foo");
    resource.setEffectiveKey("effectiveKey");
    resourceMapping.addResource(resource, "key1");
    assertThat(resourceMapping.getComponentKeyByFileKey("key1")).isNotNull();
    assertThat(resourceMapping.getComponentKeyByFileKey("key1")).isSameAs(resource.getEffectiveKey());
  }
}