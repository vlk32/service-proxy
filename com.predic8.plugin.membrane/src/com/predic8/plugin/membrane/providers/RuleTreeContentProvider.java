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

package com.predic8.plugin.membrane.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import com.predic8.membrane.core.Core;
import com.predic8.membrane.core.RuleManager;
import com.predic8.membrane.core.exchange.Exchange;
import com.predic8.membrane.core.model.IRuleTreeViewerListener;
import com.predic8.membrane.core.rules.Rule;

public class RuleTreeContentProvider implements ITreeContentProvider, IRuleTreeViewerListener {

	private TreeViewer treeViewer;

	public RuleTreeContentProvider(TreeViewer treeViewer) {
		super();
		this.treeViewer = treeViewer;
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RuleManager)
			return ((RuleManager) parentElement).getRules().toArray();
		if (parentElement instanceof Rule) {
			return Core.getExchangeStore().getExchanges(((Rule)parentElement).getRuleKey());
		}
		return new Object[] {};
	}

	
	public Object getParent(Object element) {
		if (element instanceof Exchange)
			return ((Exchange) element).getRule();
		return null;
	}

	
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput != null)
			((RuleManager) newInput).addTreeViewerListener(this);
		if (oldInput != null)
			((RuleManager) oldInput).removeTreeViewerListener(this);
	}

	public void addRule(Rule rule) {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.refresh();
			}
		});
		addExchanges(rule);

		selectTo(rule);
	}


	public void removeRule(final Rule rule, final int rulesLeft) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.remove(rule);
			}
		});
	}

	
	public void addExchange(final Rule rule, final Exchange exchange) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.add(rule, exchange);
			}
		});
		
		if (Core.getConfigurationManager().getConfiguration().getTrackExchange()) {
			selectTo(exchange);
		}
	}

	private void addExchanges(final Rule rule) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.add(rule, Core.getExchangeStore().getExchanges(rule.getRuleKey()));
			}
		});
	}

	
	public void removeExchange(final Exchange exchange) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.remove(exchange);
				treeViewer.setSelection(new StructuredSelection(exchange.getRule()));
				treeViewer.refresh();
			}
		});
	}

	public void refresh() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.refresh();
			}
		});
	}

	public void selectTo(final Object obj) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.setSelection(new StructuredSelection(obj), true);
			}
		});
	}

	public void setExchangeFinished(final Exchange exchange) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				treeViewer.refresh(exchange);
				//treeViewer.setEnableMenuStopAction(false);
			}
		});
	}

	public void removeExchanges(final Rule parent, final Exchange[] exchanges) {
		if (exchanges != null && exchanges.length > 0) {
			treeViewer.remove(parent, exchanges);
			treeViewer.remove(exchanges);
			treeViewer.setSelection(new StructuredSelection(exchanges[0].getRule()));
			refresh();	
		}
	}

}
