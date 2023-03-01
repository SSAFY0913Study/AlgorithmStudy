#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

vector<int> graph[100001];
int cheerUpArr[100001];
int n, m;

/* ���ٹ�
* 1. �ܼ� DFS���ٽ� �ð��ʰ� �߻�
* 2. ���ӻ��� �ڽ��� ��ȣ���� �۴ٴ� ������ ������ �������� dp���(�ڽ��� ���� �� �ִ� Ī���� ��� ��Ƽ� �����Ĺ迡�� ��������)
* 3. �׷��� ���ӻ��� �ڽ��� ��ȣ���� �۴ٴ� ������ ������ ���ٸ� ���Ұ�(���� ���� Ǯ�̹���� �ƴ� �� ����)
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

	for (int i = 2; i <= n; i++) {//1���� �����̴� �����ϰ� 2������ n��° �������� �ݺ�
		for (int j = 0; j < graph[i].size(); j++) {
			int directSub = graph[i][j]; //���� ������ ��ȣ
			cheerUpArr[directSub] += cheerUpArr[i];	//���Ӻ��Ͽ��� Ī������
		}
	}

	for (int i = 1; i <= n; i++) {
		cout << cheerUpArr[i] << ' ';
	}

	return 0;
}