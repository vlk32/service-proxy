/* Copyright 2009 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
package com.predic8.plugin.membrane.dialogs.rule.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.predic8.plugin.membrane.components.RuleOptionsRuleKeyGroup;

public class ForwardingRuleKeyTabComposite extends Composite {

	private RuleOptionsRuleKeyGroup ruleKeyGroup;
	
	public ForwardingRuleKeyTabComposite(Composite parent) {
		super(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginTop = 20;
		gridLayout.marginLeft = 20;
		gridLayout.marginBottom = 20;
		gridLayout.marginRight = 20;
		setLayout(gridLayout);
		
		ruleKeyGroup = new RuleOptionsRuleKeyGroup(this, SWT.NONE);
		
	}

	public RuleOptionsRuleKeyGroup getRuleOptionsRuleKeyGroup() {
		return ruleKeyGroup;
	}
	
}