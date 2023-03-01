#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

vector<int> graph[100001];
int cheerUpArr[100001];
int n, m;

void dfs(int i, int w) {//i는 직원의 번호, w는 상사로부터 이어져 내려온 칭찬의 합
	cheerUpArr[i] += w;	//위에서 내려온 칭찬의 합을 자기 자신에게 더해준다.
	for (int next : graph[i]) {// 자신의 직속부하에게 자신의 칭찬 총합을 전달해준다.
		dfs(next, cheerUpArr[i]);
	}
}


int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> n >> m;

	int superior;
	for (int i = 1; i <= n; i++) {
		cin >> superior;
		if (superior == -1) continue;

		graph[superior].push_back(i);
	}

	int empNum, cheerUp;
	for (int i = 0; i < m; i++) {
		cin >> empNum >> cheerUp;
		cheerUpArr[empNum] += cheerUp;
	}

	dfs(1, 0);

	for (int i = 1; i <= n; i++) {
		cout << cheerUpArr[i] << ' ';
	}

	return 0;
}