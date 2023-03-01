#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

vector<int> graph[100001];
int cheerUpArr[100001];
int n, m;

void dfs(int i, int w) {//i�� ������ ��ȣ, w�� ���κ��� �̾��� ������ Ī���� ��
	cheerUpArr[i] += w;	//������ ������ Ī���� ���� �ڱ� �ڽſ��� �����ش�.
	for (int next : graph[i]) {// �ڽ��� ���Ӻ��Ͽ��� �ڽ��� Ī�� ������ �������ش�.
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