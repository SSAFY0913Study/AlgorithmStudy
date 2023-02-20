#include<iostream>
#include<string>
#include<vector>
#include<cstring>
#include<algorithm>
#include<queue>
#include<utility>
#include<cmath>
using namespace std;
/*
1. 입력받기

2. bfs로 전체 순회하면서 국가별 연합 확인 및 연합의 수 체크
2-1. 연합별 인구 총합, 연합에 속해있는 나라 수 체크

3. 각각 연합 체크하면서 인구 이동 시작 

4. 무한 반복하면서 (연합마다 인원 분배 되있으면, 연합의 수가 n*n이면) stop
*/
typedef pair<int, int>p;
int dx[4] = { -1,1,0,0 }, dy[4] = { 0,0,-1,1 };
int n, mini, maxi, ans, sum;
int arr[50][50], check[50][50];
bool flag;
queue<p>q;
vector<vector<p>>posv;
vector<int>sumv;
void input() {
	cin >> n >> mini >> maxi;
	for (int i = 0; i < n; i++)for (int j = 0; j < n; j++)cin >> arr[i][j];
}
bool canMove(int a, int b) {
	return mini <= abs(a - b) && abs(a - b) <= maxi;
}
void bfs(int tmp,int s) {
	int count = 1;
	while (!q.empty()) {
		int x = q.front().first, y = q.front().second;
		q.pop();
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i], ny = y + dy[i];
			if (nx<0 || nx>n - 1 || ny<0 || ny>n - 1||check[nx][ny]|| !canMove(arr[x][y], arr[nx][ny]))continue;//범위값 초과 혹은 방문했다면 혹은 차이가 min~maxi값이 아니라면
			check[nx][ny] = tmp;
			q.push({ nx,ny });
			s += arr[nx][ny]; //연합별 나라의 인구 수 합 구하기
			posv[tmp - 1].push_back({ nx,ny });//연합별 좌표 저장
		}
	}
	sumv.push_back(s);
}
int search() { //인구이동 전, bfs를 통해 연합 갯수 확인
	int connectionCnt = 0;
	posv.clear();
	sumv.clear();
	posv.resize(n * n);
	memset(check, 0, sizeof(check));
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (check[i][j])continue;
			q.push({ i,j });
			check[i][j] = ++connectionCnt;
			bfs(connectionCnt, arr[i][j]); //bfs를 돌며 각 연합의 인구 합, 연결성분 내의 노드(나라)갯수 카운팅
			posv[connectionCnt - 1].push_back({ i,j });
		}
	}
	return connectionCnt; //연합의 수 return
}
int move(int conn) { 
	if (conn == n * n)return 0; //연합의 수가 n*n이면(=모두가 이동할 수 없는 상태이면) return 0
	int cnt = 0;
	for (int i = 0; i < conn; i++) {
		int s = posv[i].size();
		int tmp = sumv[i] / s; //연합의 인구수/연합의 나라 수
		for (int j = 0; j < s; j++) {
			int x = posv[i][j].first, y = posv[i][j].second;
			if (arr[x][y] == tmp) { //이미 평균인구와 같은 나라 수 카운팅
				cnt++;
			}
			arr[x][y] = tmp; //인구 평준화
		}
	}
	ans++; //인구 이동 횟수
	if (cnt == n * n) return 0;//이미 n*n개의 나라가 모두 평준화가 되어있다면 return 0;
	return 1;
}
int main() {
	ios_base::sync_with_stdio(false), cin.tie(0), cout.tie(0);
	input();
	while (move(search())) {} //연합 갯수 탐색 + 인구이동
	cout << ans << "\n";
	return 0;
}

