#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
#include <queue>
using namespace std;

int	N, K, zeroCnt = 0, result = 0;
vector<pair<int, bool>> conveyorBelt;

void simul();

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> N >> K;

	int durability;
	for (int i = 0; i < 2 * N; i++) {
		cin >> durability;
		conveyorBelt.push_back(make_pair(durability, false));
	}

	simul();
	cout << result;

	return 0;
}

void rotate() {
	pair<int, bool> last = conveyorBelt[conveyorBelt.size() - 1];
	conveyorBelt.pop_back();
	conveyorBelt.insert(conveyorBelt.begin(), last);

	if (conveyorBelt[N - 1].second == true) {//������ ĭ�� �κ��� ������� ������
		conveyorBelt[N - 1].second = false;
	}
}

void moveRobot() {

	for (int i = N - 1; i > 0; i--) {
		if (conveyorBelt[i - 1].second == true && conveyorBelt[i].first > 0 && conveyorBelt[i].second == false) {
			conveyorBelt[i].second = true;
			conveyorBelt[i].first--; //������ ����
			if (conveyorBelt[i].first == 0) zeroCnt++;
			conveyorBelt[i - 1].second = false;
		}
	}

	if (conveyorBelt[N - 1].second == true) {//������ ĭ�� �κ��� ������� ������
		conveyorBelt[N - 1].second = false;
	}

	if (conveyorBelt[0].first > 0) {//�ø��� ĭ�� �κ� �ø���
		conveyorBelt[0].second = true;
		conveyorBelt[0].first--; //������ ����
		if (conveyorBelt[0].first == 0) zeroCnt++;
	}
}

void simul() {
	int count = 1;
	while (true) {
		rotate();
		moveRobot();
		if (zeroCnt >= K) {
			result = count;
			return;
		}
		count++;
	}
}