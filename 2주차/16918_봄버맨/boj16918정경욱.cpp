#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

int R, C, N;
char input;

int map[202][202];
int dx[4] = { 0,1,0,-1 };
int dy[4] = { 1,0,-1,0 };

void putBoom();
void checkBoom();


void simul() {
	int curtime = 1;

	while (curtime < N) {
		putBoom();
		checkBoom();
		curtime++;
	}
}

void putBoom() {	// 폭탄 설치함수
	for (int i = 1; i <= R; i++) {
		for (int j = 1; j <= C; j++) {
			if (map[i][j] == -1) map[i][j] = 4; //빈칸인 곳에 폭탄을 설치, 4로 설정한 이유는 checkedBoom()함수에서 바로 감소시켜주기 때문임. 
		}
	}
}

void checkBoom() {
	vector<pair<int, int>> curBoom;	//이번 턴에 터지는 폭탄의 좌표를 저장하는 배열

	for (int i = 1; i <= R; i++) {
		for (int j = 1; j <= C; j++) {
			if (map[i][j] > 0) map[i][j] --;	//0보다 큰값이면 값을 1씩빼준다
			if (map[i][j] == 0) {	// 0이되었으면 이번턴에 터진다.
				map[i][j] = -1;	// 터지는 곳을 빈칸으로 세팅
				curBoom.push_back(make_pair(i, j));	// 배열에 추가
			}
		}
	}

	for (int i = 0; i < curBoom.size(); i++) {// 배열을 전부 돌면서 주변을 빈칸으로 만든다.
		for (int j = 0; j < 4; j++) {
			map[curBoom[i].first + dx[j]][curBoom[i].second + dy[j]] = -1;
		}
	}
}

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);


	cin >> R >> C >> N;

	for (int i = 1; i <= R; i++) {
		for (int j = 1; j <= C; j++) {
			cin >> input;
			if (input == '.') map[i][j] = -1;
			else map[i][j] = 3;
		}
	}

	checkBoom();	//1초후
	if (N >= 2) simul();

	for (int i = 1; i <= R; i++) {//출력
		for (int j = 1; j <= C; j++) {
			if (map[i][j] == -1) cout << '.';
			else cout << 'O';
		}
		cout << '\n';
	}

	return 0;
}