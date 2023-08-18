/*
 * Copyright 1999-2023 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.plugin.datasource.dialect;

import com.alibaba.nacos.common.utils.NamespaceUtil;
import com.alibaba.nacos.plugin.datasource.constants.DatabaseTypeConstant;

/***
 * oracle datasource dialect.
 * @author onewe
 */
public class OracleDatabaseDialect extends AbstractDatabaseDialect {
	
	private static final String DEFAULT_NAMESPACE_ID = "PUBLIC";

	public static final String SELECT = "SELECT";

	public static final String WHERE = "WHERE";

	static {
		NamespaceUtil.namespaceDefaultId = DEFAULT_NAMESPACE_ID;
	}

	@Override
	public String getType() {
		return DatabaseTypeConstant.ORACLE;
	}

	public static String wrapSingleSQLByPage(String sql, int starRow, int pageSize) {
		StringBuilder sb = new StringBuilder("SELECT * FROM ( SELECT ROWNUM,");
		int endRow = starRow + pageSize;
		int selectIndex = sql.indexOf(SELECT);
		int whereIndex = sql.indexOf(WHERE);
		if (whereIndex == -1) {
			sb.append(sql, selectIndex + SELECT.length(), sql.length())
					.append(" WHERE ROWNUM <= ").append(endRow);
		} else {
			sb.append(sql, selectIndex + SELECT.length(), whereIndex)
					.append(" WHERE ROWNUM <= ").append(endRow).append(" AND ")
					.append(sql, whereIndex + WHERE.length(), sql.length());
		}
		sb.append(") WHERE ROWNUM > ").append(starRow);
		return sb.toString();
	}


	@Override
	public String getLimitPageSqlWithOffset(String sql, int startOffset, int pageSize) {
		return wrapSingleSQLByPage(sql, startOffset, pageSize);
	}
	
}
