#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

vector<int> graph[100001];
int cheerUpArr[100001];
int n, m;

/* 접근법
* 1. 단순 DFS접근시 시간초과 발생
* 2. 직속상사는 자신의 번호보다 작다는 문제의 조건을 바탕으로 dp사용(자신이 받을 수 있는 칭찬을 모두 모아서 직속후배에게 전달하자)
* 3. 그러나 직속상사는 자신의 번호보다 작다는 문제의 조건이 없다면 사용불가(따라서 좋은 풀이방법은 아닌 것 같음)
*/
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

	for (int i = 2; i <= n; i++) {//1번은 사장이니 제외하고 2번부터 n번째 직원까지 반복
		for (int j = 0; j < graph[i].size(); j++) {
			int directSub = graph[i][j]; //직속 부하의 번호
			cheerUpArr[directSub] += cheerUpArr[i];	//직속부하에게 칭찬전달
		}
	}

	for (int i = 1; i <= n; i++) {
		cout << cheerUpArr[i] << ' ';
	}

	return 0;
}